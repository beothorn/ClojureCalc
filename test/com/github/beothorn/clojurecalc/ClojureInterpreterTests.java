package com.github.beothorn.clojurecalc;

import junit.framework.TestCase;

public class ClojureInterpreterTests extends TestCase{
    public void testExp(){
        runAndAssert("", "");
        //TODO: runAndAssert("(1)", "1");
        runAndAssert("(+ 1 2)", "3");
        runAndAssert("(map #(* % 2) [1 2 3])", "[2 4 6]");
        runAndAssert("[ [ 2 4 6 ] ]", "[[2 4 6]]");
    }    

    private void runAndAssert(final String exp, final String expected) {
        final String result = ClojureInterpreter.runClojure(exp);
        junit.framework.Assert.assertEquals(expected, result);
    }
}
