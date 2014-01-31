package com.poliana.core.common.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 1/30/14
 */
public class StringProcessingServiceTest {

    private StringProcessingService process;

    @Before
    public void setUp() throws Exception {

        this.process = new StringProcessingService();
    }

    /**
     * Tests borrowed from http://www.coderanch.com/t/372746/java/java/method-convert-Mixed-Case
     * @throws Exception
     */
    @Test
    public void testToMixed() throws Exception {

        assertEquals("", process.toMixed(""));
        assertEquals("F", process.toMixed("F"));
        assertEquals("Foo", process.toMixed("FOO"));
        assertEquals("Foo Bar", process.toMixed("FOO BAR"));
        assertEquals("Foo Bar Baz", process.toMixed("FOO BAR BAZ"));
        assertEquals(" Foo Bar Baz ", process.toMixed(" FOO BAR BAZ "));
        assertEquals("This Is Already Mixed", process.toMixed("This Is Already Mixed"));
        assertEquals("Partly Mixed Partly Not", process.toMixed("Partly Mixed PARTLY NOT"));
        assertEquals("And, What Of-- Punctuation?!!", process.toMixed("AND, WHAT OF-- PUNCTUATION?!!"));
    }
}
