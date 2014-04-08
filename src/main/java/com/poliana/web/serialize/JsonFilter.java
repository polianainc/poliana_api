package com.poliana.web.serialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author David Gilmore
 * @date 4/7/14
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {
    Class<?> mixin();
}