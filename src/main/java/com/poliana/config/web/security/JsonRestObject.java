package com.poliana.config.web.security;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joseph Vartuli
 * @date 25/08/12
 */
public class JsonRestObject {

    private Integer id;
    private String name;
    private List<String> relatedObjects = new ArrayList<String>();

    public JsonRestObject(Integer id) {
        this.id = id;
        name = "My Restful Json Object";
        relatedObjects.add("relatedObjectA");
        relatedObjects.add("relatedObjectB");
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getRelatedObjects() {
        return relatedObjects;
    }
}
