package com.poliana.web.common;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * @author David Gilmore
 * @date 12/31/13
 */
public abstract class AbstractBaseController {

    protected Gson gson;

    public AbstractBaseController() {


        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                //Our in house wrappers are snake case. Our database naming convention is snake case.
                //Thus all controllers return snake case json
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                //Exclude any fields with @Expose annotations
                .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .create();
    }
}
