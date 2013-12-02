package com.poliana.common.db;

import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

/**
 * @author David Gilmore
 * @date 11/28/13
 */
@Component
@PropertySource({"classpath:ideology.properties","classpath:mongo.properties"})
public class MahoutUtil {

    @Autowired
    private Environment env;

    public DataModel mongoModel(String collection) {
        try {
            return new MongoDBDataModel(
                    env.getProperty(""),
                    Integer.parseInt(env.getProperty("")),
                    env.getProperty(""),
                    collection,
                    Boolean.getBoolean(env.getProperty("")),
                    Boolean.getBoolean(env.getProperty("")),
                    new SimpleDateFormat(env.getProperty(""))
            );
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

}
