package com.github.beothorn.clojurecalc;

import junit.framework.TestCase;
import junit.framework.Assert;

public class ClojureInterpreterTests extends TestCase{
    public void testExp(){
        runAndAssert("", "");
        //TODO: runAndAssert("(1)", "1");
        runAndAssert("(+ 1 2)", "3");
        runAndAssert("(map #(* % 2) [1 2 3])", "[2 4 6]");
        runAndAssert("[ [ 2 4 6 ] ]", "[[2 4 6]]");
    }    
    
    public void testCellsToMatrix(){
        final String clojureCollection = ClojureInterpreter.toClojureCollection(new String[][]{{"1", "2", "3"}});
        Assert.assertEquals("[1 2 3]", clojureCollection);
        final String clojureCollectionCollections = ClojureInterpreter.toClojureCollection(new String[][]{{"1", "2", "3"},{"4", "5", "6"}});
        Assert.assertEquals("[[1 2 3] [4 5 6]]", clojureCollectionCollections);
    }

    private void runAndAssert(final String exp, final String expected) {
        final String result = ClojureInterpreter.runClojure(exp);
        Assert.assertEquals(expected, result);
    }
}
