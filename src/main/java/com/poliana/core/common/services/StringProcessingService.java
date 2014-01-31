package com.poliana.core.common.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author David Gilmore
 * @date 1/30/14
 */
public class StringProcessingService {

    private Pattern PATTERN = Pattern.compile("\\b(\\w)(\\w*)\\b");

    /**
     * Function borrowed from http://www.coderanch.com/t/372746/java/java/method-convert-Mixed-Case
     * @param input
     * @return
     */
    public String toMixed(String input) {
        StringBuffer buff = new StringBuffer(input.length());
        Matcher matcher = PATTERN.matcher(input);
        int nextInputPos = 0;
        while (matcher.find()) {
            buff.append(input.substring(nextInputPos, matcher.start()));
            buff.append(matcher.group(1).toUpperCase());
            buff.append(matcher.group(2).toLowerCase());
            nextInputPos = matcher.end();
        }
        buff.append(input.substring(nextInputPos));
        return buff.toString();
    }
}
