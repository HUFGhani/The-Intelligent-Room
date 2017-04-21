package com.project.smarthome.smarthome;


import com.project.smarthome.smarthome.Utilities.TextUtilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextUtilitiesTest {

    @Test
    public void isNullOrWhitespace_isCorrect() throws Exception {
        assertEquals("null is true", true, TextUtilities.isNullOrWhiteSpace(null));
        assertEquals("empty string is true", true, TextUtilities.isNullOrWhiteSpace(""));
        assertEquals("whitespace is true", true, TextUtilities.isNullOrWhiteSpace("   "));
        assertEquals("content is false", false, TextUtilities.isNullOrWhiteSpace("content"));
    }

    @Test
    public void validateName_isCorrect() throws Exception {
        assertEquals("karl is true", true, TextUtilities.validateName("karl"));
        assertEquals("name with number is false", false, TextUtilities.validateName("karl3"));
        assertEquals("whitespace is false", false, TextUtilities.validateName("    "));
        assertEquals("null is false", false, TextUtilities.validateName(null));
        assertEquals("empty string is false", false, TextUtilities.validateName(""));
    }

    @Test
    public void containsNumber_isCorrect() throws Exception {
        assertEquals("6 is true", true, TextUtilities.containsNumber("6"));
        assertEquals("t3st is true", true, TextUtilities.containsNumber("t3st"));
        assertEquals("whitespace is false", false, TextUtilities.containsNumber("    "));
        assertEquals("null is false", false, TextUtilities.containsNumber(null));
        assertEquals("empty string is false", false, TextUtilities.containsNumber(""));
        assertEquals("test string is false", false, TextUtilities.containsNumber("test"));
    }

}
