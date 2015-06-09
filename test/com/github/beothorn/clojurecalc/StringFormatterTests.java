package com.github.beothorn.clojurecalc;

import junit.framework.TestCase;

public class StringFormatterTests extends TestCase{
    public void testSimpleFormatter(){
        final String actual = StringFormatter.format("{0} {1} {2}", new Object[]{1, 2, 3});
        final String expected = "1 2 3";
        assertEquals(expected, actual);
    }  
    public void testRangeFormatter(){
        final String actual = StringFormatter.format("{0} {1}", 
                                new Object[]{
                                    1,
                                    new Object[][]{
                                        new Object[]{2, 3}, 
                                        new Object[]{4, "foo"}}});
        final String expected = "1 2 3 4 foo";
        assertEquals(expected, actual);
    }  
}
