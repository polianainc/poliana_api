package com.poliana.core.pacs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import com.google.gson.annotations.Expose;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Entity("pacs")
@JsonIgnoreProperties({"id"})
public class PAC {

    @Id
    @Expose
    private String id;

    private String name;
    @Property("filed_names")
    private String filedNames;
    private String treasurer;

}
