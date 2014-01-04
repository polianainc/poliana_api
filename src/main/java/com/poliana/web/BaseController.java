package com.poliana.web;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author David Gilmore
 * @date 12/31/13
 */
public abstract class BaseController {

    protected Gson gson;

    public BaseController() {

        //Our in house wrappers are snake case. Our database naming convention is snake case.
        //Thus all controllers return snake case json
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
