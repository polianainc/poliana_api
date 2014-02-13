package com.poliana.core.common.streaming;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.xml.xpath.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Grayson Carroll
 * @date 1/31/14
 */
@Service
public class CrpStreamingService {

    private XPathFactory factory;
    private XPath xpath;
    private InputSource crpXmlInput;
    private JedisPool jedisPool;
    private AmazonS3 s3Connection;
    private String localTmp;
    private String s3Bucket;

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    private URL metadataUrl;
    private URL downloadUrlRoot;

    private final String CRP_MOST_RECENT = "crpMostRecent:";
    private final String CRP_NEEDS_UPDATE = "crpNeedsUpdate:";

    private static final Logger logger = Logger.getLogger(CrpStreamingService.class);

    public CrpStreamingService() {

        factory = XPathFactory.newInstance();
        xpath = factory.newXPath();

        setLocalTmp("./tmp/");
        setS3Bucket("poliana.prod");

        try {
            this.setMetadataUrl(new URL("http://www.opensecrets.org/myos/odata_meta.xml"));
        } catch (MalformedURLException e) {
            logger.error(e);
        }

        try {
            this.setDownloadUrlRoot(new URL("http://www.opensecrets.org/myos/download.php?f="));
        } catch (MalformedURLException e) {
            logger.error(e);
        }
    }

    public void sync() {

        try {
            crpXmlInput = new InputSource(this.metadataUrl.openStream());
        } catch (IOException e) {
            logger.error(e);
        }

        try {

            // Creates an expression that will grab all the top level file objects in the XML
            XPathExpression filesExpr = xpath.compile("/opendata_files/file");

            //Grabs those files and stores them in a list
            Object result = filesExpr.evaluate(crpXmlInput, XPathConstants.NODESET);
            NodeList files = (NodeList) result;

            // Creates expressions to pull the necessary information for each file
            XPathExpression timestampExpr = xpath.compile("tstamp");
            XPathExpression filenameExpr = xpath.compile("filename");

            //Create a list to accumulate all conflicts as we diff Redis and CRP
            List<String> versionConflicts = new LinkedList<>();

            //Loops over every file, storing the filenames and timestamps as key-value pairs in Redis
            for(int i = 0; i < files.getLength(); i++) {

                Node n = files.item(i);

                if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {

                    Element file = (Element) n;

                    String filename = filenameExpr.evaluate(file);
                    String timestamp = timestampExpr.evaluate(file);

                    updateMostRecentCrp(versionConflicts, filename, timestamp);
                }
            }

            //Send notifications to administrators that a job needs to be run
            notifyVersionConflict(versionConflicts);

        } catch (XPathExpressionException e) {
            logger.error(e);
        }

    }

    private List<String> updateMostRecentCrp(List<String> versionConflicts, String filename, String timestamp) {

        Jedis jedis = jedisPool.getResource();
        filename = filename.split("\\.")[0];

        try {
            if(!jedis.exists(CRP_MOST_RECENT + filename)) {
                //If this is the first run, just write it
                jedis.set(CRP_MOST_RECENT + filename, timestamp);

            } else {
                long oldTimestamp = Long.parseLong(jedis.get(filename), 10);
                long newTimestamp = Long.parseLong(timestamp, 10);

                //If there is a differential, handle the differential and write the new value to redis
                if(oldTimestamp != newTimestamp) {

                    versionConflicts.add(filename);
                    jedis.set(CRP_MOST_RECENT + filename, timestamp);
                } else {

                    //if no differential, there's nothing else to do. return
                    return versionConflicts;
                }
            }
        } catch (JedisConnectionException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }

        return versionConflicts;
    }

    public void notifyVersionConflict(List<String> versionConflicts) {

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);


        String messageListOfConflicts = "\n";

        for (String conflict : versionConflicts) {

            messageListOfConflicts += conflict + "\n";

            updateOutOfSync(conflict);
        }

        msg.setTo(new String[] {"grayson@poliana.com", "david@poliana.com"});
        msg.setSubject("CRP data");
        msg.setText("Yo, it's Spring. You need to get yo shit together and download some CRP data:" + messageListOfConflicts);

        try{
            this.mailSender.send(msg);
        }
        catch(MailException ex) {
            System.err.println(ex.getMessage());
        }

        return;
    }

    public void updateOutOfSync(String conflict) {

        Jedis jedis = jedisPool.getResource();

        try {

            jedis.set(CRP_NEEDS_UPDATE + conflict, "true");

        } catch (JedisConnectionException e) {
            if (null != jedis) {
                jedisPool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis)
                jedisPool.returnResource(jedis);
        }
    }

    public void handleFileVersionConflict(String filename) {

        String saveTo = "./tmp/";
        filename += ".zip";

        boolean success = downloadZip(filename);

        if (success) {

            boolean unzip = unzip(filename);
        }

        return;
    }

    public boolean authenticate() {

        //TODO: Authenticate
        return false;
    }

    public boolean downloadZip(String filename) {

        String downloadUrl = downloadUrlRoot + filename;

        File file = new File(localTmp);
        if (!file.exists()) {
            if (file.mkdir()) {
                logger.info("Directory is created!");
            } else {
                logger.error("Failed to create directory!");
            }
        }

        try {

            URL url = new URL(downloadUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(localTmp + filename);

            byte[] b = new byte[1024];

            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }

            out.flush();
            out.close();
            in.close();

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean unzip(String filename) {

        byte[] buffer = new byte[1024];

        try{

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(localTmp + filename));

            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while ( ze != null ){

                //Get the name of this file
                String fileName = ze.getName();

                //Open a new file with the name
                File newFile = new File(localTmp + fileName);

                logger.info("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                //Open a stream for the unzipped data
                FileOutputStream fos = new FileOutputStream(newFile);


                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len); //Iterate through the unzip buffer writing to the new file
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            logger.info("Finished Unzipping " + filename);

        }
        catch(IOException e){

            logger.error(e);
            return false;
        }

        return true;
    }

    public boolean update() {

        boolean success = true;

        File tmpDir = new File(localTmp);

        for (File child : tmpDir.listFiles()) {

            String s3Path = "campaign_finance/crp/";

            String name = child.getName().split("\\.")[0].split("\\d+")[0];


            //Decide which directory the file should be put into
            switch (name) {

                case ("cands") : s3Path += "candidate_contributions"; break;
                case ("cmtes") : s3Path += "committee_contributions"; break;
                case ("expends") : s3Path += "expenditures"; break;
                case ("indivs") : s3Path += "individual_contributions"; break;
                case ("pacs") : s3Path += "pac_to_cand_contributions"; break;
                case ("pac_other") : s3Path += "pac_to_pac_contributions"; break;

                default : s3Path = "";
            }

            if (!s3Path.equals("")) {

                s3Path += File.separator + child.getName();

                try {

                    s3Connection.putObject(s3Bucket, s3Path, child);
                    logger.info("Updated s3 with " + child.getName());
                }
                catch (AmazonClientException e) {

                    success = false;
                    logger.error(e);
                }
            }
        }

        return success;
    }

    public void setMetadataUrl(URL metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    public void setDownloadUrlRoot(URL downloadUrlRoot) {
        this.downloadUrlRoot = downloadUrlRoot;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Autowired
    public void setS3Connection(AmazonS3 s3Connection) {
        this.s3Connection = s3Connection;
    }

    public void setLocalTmp(String localTmp) {
        this.localTmp = localTmp;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }

    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}
