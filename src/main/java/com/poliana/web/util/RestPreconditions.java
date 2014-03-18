package com.poliana.web.util;

import com.poliana.web.error.InvalidMethodArgumentException;
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
    private static final String PAC_PATTERN = "[A-Z]{1}\\d{8}";

    private RestPreconditions() {
        throw new AssertionError();
    }

    // API

    /**
     * Check if the bioguideId is valid. If not, through a 400
     *
     * @param bioguideId
     */
    public static void checkValidBioguideId(final String bioguideId) {

        Pattern pattern = Pattern.compile(BIOGUIDE_PATTERN);
        Matcher matcher = pattern.matcher(bioguideId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new InvalidMethodArgumentException("Malformed bioguide ID");
        }
    }

    /**
     * Check if the industryId is valid. If not, through a 400
     *
     * @param industryId
     */
    public static void checkValidIndustryId(final String industryId) {

        Pattern pattern = Pattern.compile(INDUSTRY_PATTERN);
        Matcher matcher = pattern.matcher(industryId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new InvalidMethodArgumentException("Malformed industry ID");
        }
    }

    /**
     * Check if the categoryId is valid. If not, through a 400
     *
     * @param categoryId
     */
    public static void checkValidIndustryCategoryId(final String categoryId) {

        Pattern pattern = Pattern.compile(INDUSTRY_CATEGORY_PATTERN);
        Matcher matcher = pattern.matcher(categoryId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new InvalidMethodArgumentException("Malformed industry category ID");
        }
    }

    /**
     * Check if the pacId is valid. If not, through a 400
     *
     * @param pacId
     */
    public static void checkValidPacId(final String pacId) {

        Pattern pattern = Pattern.compile(PAC_PATTERN);
        Matcher matcher = pattern.matcher(pacId);

        boolean valid = matcher.matches();

        if (!valid) {
            throw new InvalidMethodArgumentException("Malformed PAC ID");
        }
    }
}