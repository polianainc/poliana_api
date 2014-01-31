package com.poliana.streaming;

import org.msgpack.MessagePack;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.xml.xpath.*;
import java.io.IOException;
import java.net.URL;

/**
 * @author Grayson Carroll
 * @date 1/31/14
 */
public class OpenSecretsSync {

    private XPathFactory factory;
    private XPath xpath;
    private InputSource crpXmlInput;
    private JedisPool jedisPool;
    Jedis jedis;



    public OpenSecretsSync() throws IOException {
        factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        String url = "http://www.opensecrets.org/myos/odata_meta.xml";
        crpXmlInput = new InputSource(new URL(url).openStream());
        jedis = jedisPool.getResource();
    }

    public void sync() {

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
            e.printStackTrace();
        }

    }

    private void updateRedisRecord(String filename, String timestamp) {

        MessagePack messagePack = new MessagePack();
        try {

            if(!jedis.exists(messagePack.write(filename))) {
                //If this is the first run, just write it
                jedis.lpush(messagePack.write(filename), messagePack.write(timestamp));
            } else {
                long oldTimestamp = Long.parseLong(jedis.get(filename), 10);
                long newTimestamp = Long.parseLong(timestamp, 10);

                //If there is a differential, handle the differential and write the new value to redis
                if(oldTimestamp != newTimestamp) {
                    handleFileVersionConflict(filename);
                    jedis.set(filename, timestamp);
                } else {
                    //if no differential, there's nothing else to do. return
                    return;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }


    }

    private void handleFileVersionConflict(String filename) {
        // TODO: Implement the handling of a different version of the open secrets bulk data
        return;
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static void main(String[] args) {

        try {
            OpenSecretsSync osSync = new OpenSecretsSync();
            osSync.sync();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
