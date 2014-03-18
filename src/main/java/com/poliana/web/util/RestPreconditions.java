package com.poliana.web.util;

import com.poliana.web.error.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple static methods used to validate input. If the Precondition fails, an {@link HttpStatus} code is thrown
 * @author David Gilmore
 * @date 3/16/14
 */
public final class RestPreconditions {

    private static final String BIOGUIDE_PATTERN = "[A-Z]{1,2}\\d{6}";
    private static final String INDUSTRY_PATTERN = "[A-Z]{1}\\d{2}";
    private static final String INDUSTRY_CATEGORY_PATTERN = "[A-Z]{1}\\d{4}";

    private RestPreconditions() {
        throw new AssertionError();
    }

    // API

    /**
     * Check if the bioguideId is valid
     *
     * @param bioguideId
     *            has value true if found, otherwise false
     * @throws ResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkValidBioguideId(final String bioguideId) {

        Pattern pattern = Pattern.compile(BIOGUIDE_PATTERN);
        Matcher matcher = pattern.matcher(bioguideId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Check if the industryId is valid
     *
     * @param industryId
     *            has value true if found, otherwise false
     * @throws ResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkValidIndustryId(final String industryId) {

        Pattern pattern = Pattern.compile(INDUSTRY_PATTERN);
        Matcher matcher = pattern.matcher(industryId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Check if the categoryId is valid
     *
     * @param categoryId
     *            has value true if found, otherwise false
     * @throws ResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkValidIndustryCategoryId(final String categoryId) {

        Pattern pattern = Pattern.compile(INDUSTRY_CATEGORY_PATTERN);
        Matcher matcher = pattern.matcher(categoryId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new ResourceNotFoundException();
        }
    }
}