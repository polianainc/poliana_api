package com.poliana.core.pacs;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

/**
 * @author David Gilmore
 * @date 12/26/13
 */
@Entity("pacs")
public class PAC {

    @Id
    private String id;

    private String name;
    @Property("filed_names")
    private String filedNames;
    private String treasurer;

}
