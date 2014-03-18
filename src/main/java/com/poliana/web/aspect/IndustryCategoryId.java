package com.poliana.web.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Used to flag aspect code of a bioguideId field
 *
 * @author David Gilmore
 * @date 3/17/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface IndustryCategoryId {}