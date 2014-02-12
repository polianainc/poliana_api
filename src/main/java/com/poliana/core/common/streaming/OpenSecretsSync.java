package com.poliana.core.common.streaming;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Grayson Carroll
 * @date 1/31/14
 */
@Component
public class OpenSecretsSync {

    private XPathFactory factory;
    private XPath xpath;
    private InputSource crpXmlInput;
    private JedisPool jedisPool;

    private URL metadataUrl;
    private URL downloadUrlRoot;

    private static final Logger logger = Logger.getLogger(OpenSecretsSync.class);

    public OpenSecretsSync() {
        factory = XPathFactory.newInstance();
        xpath = factory.newXPath();

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

            //Loops over every file, storing the filenames and timestamps as key-value pairs in Redis
            for(int i = 0; i < files.getLength(); i++) {
                Node n = files.item(i);

                if (n != null && n.getNodeType() == Node.ELEMENT_NODE) {
                    Element file = (Element) n;
                    String filename = filenameExpr.evaluate(file);
                    String timestamp = timestampExpr.evaluate(file);

                    updateRedisRecord(filename, timestamp);
                }
            }
        } catch (XPathExpressionException e) {
            logger.error(e);
        }

    }

    private void updateRedisRecord(String filename, String timestamp) {

        Jedis jedis = jedisPool.getResource();
        filename = filename.split("\\.")[0];
        try {
            if(!jedis.exists(filename)) {
                //If this is the first run, just write it
                jedis.set(filename, timestamp);

            } else {
                long oldTimestamp = Long.parseLong(jedis.get(filename), 10);
                long newTimestamp = Long.parseLong(timestamp, 10);

                //If there is a differential, handle the differential and write the new value to redis
                if(oldTimestamp != newTimestamp) {
                    notifyVersionConflict(filename);
                    jedis.set(filename, timestamp);
                } else {
                    //if no differential, there's nothing else to do. return
                    return;
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


    }

    public void notifyVersionConflict(String filename) {

        //TODO: Use that one awesome SMS library that patrick found, AND redis dump of files
        return;
    }

    public void handleFileVersionConflict(String filename) {

        String saveTo = "./tmp/";
        filename += ".zip";

        boolean success = downloadZip(saveTo, filename);

        if (success) {

            boolean unzip = unzip(saveTo, filename);
        }

        return;
    }

    public boolean authenticate() {

        //TODO: Authenticate
        return false;
    }

    public boolean downloadZip(String saveTo, String filename) {

        String downloadUrl = downloadUrlRoot + filename;

        File file = new File(saveTo);
        if (!file.exists()) {
            if (file.mkdir()) {
                logger.info("Directory is created!");
            } else {
                logger.error("Failed to create directory!");
            }
        }

        try {

            //TODO: If it exists, remove it!
            URL url = new URL(downloadUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(saveTo + filename);

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

    public boolean unzip(String tmpDir, String filename) {

        byte[] buffer = new byte[1024];

        try{

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(tmpDir + filename));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File(tmpDir + fileName);

                logger.info("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            logger.info("Finished Unzipping " + filename);

        }catch(IOException ex){

            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean update() {

        return false;
    }

    public void setMetadataUrl(URL metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    public void setDownloadUrlRoot(URL downloadUrlRoot) {
        this.downloadUrlRoot = downloadUrlRoot;
    }

    public void setCrpXmlInput(InputSource crpXmlInput) {
        this.crpXmlInput = crpXmlInput;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
