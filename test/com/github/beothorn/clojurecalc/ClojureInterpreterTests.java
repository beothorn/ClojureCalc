package com.github.beothorn.clojurecalc;

import java.util.List;
import junit.framework.TestCase;
import junit.framework.Assert;

public class ClojureInterpreterTests extends TestCase{
    public void testExp(){
        runAndAssert("", "");
        runAndAssert("(+ 1 2)", "3");
        runAndAssert("(map #(* % 2) [1 2 3])", "[2 4 6]");
        runAndAssert("[ [ 2 4 6 ] ]", "[[2 4 6]]");
        runAndAssert("(str '''foo''')", "foo");
        runAndAssert("(str '''''')", "");
    }    
    
    public void testNumericCellsToMatrix(){
        final String clojureCollection = ClojureInterpreter.toClojureCollectionNumber(new String[][]{{"1", "2", "3"}});
        Assert.assertEquals("[1 2 3]", clojureCollection);
        final String clojureCollectionWithEmpties = ClojureInterpreter.toClojureCollectionNumber(new String[][]{{"1", ""}});
        Assert.assertEquals("[1 0]", clojureCollectionWithEmpties);
        final String clojureCollectionCollections = ClojureInterpreter.toClojureCollectionNumber(new String[][]{{"1", "2", "3"},{"4", "5", "6"}});
        Assert.assertEquals("[[1 2 3] [4 5 6]]", clojureCollectionCollections);
        final String clojureCollectionColumn = ClojureInterpreter.toClojureCollectionNumber(new String[][]{{"1"},{"2"},{"3"}});
        Assert.assertEquals("[1 2 3]", clojureCollectionColumn);
        final String clojureCollectionSingleNumber = ClojureInterpreter.toClojureCollectionNumber(new String[][]{{"1"}});
        Assert.assertEquals("[1]", clojureCollectionSingleNumber);
    }
    
    public void testStringCellsToMatrix(){
        final String clojureCollection = ClojureInterpreter.toClojureCollectionString(new String[][]{{"1", "2", "3"}});
        Assert.assertEquals("['''1''' '''2''' '''3''']", clojureCollection);
        final String clojureCollectionWithEmpties = ClojureInterpreter.toClojureCollectionString(new String[][]{{"1", ""}});
        Assert.assertEquals("['''1''' '''''']", clojureCollectionWithEmpties);
        final String clojureCollectionCollections = ClojureInterpreter.toClojureCollectionString(new String[][]{{"1", "2"},{"4", "5"}});
        Assert.assertEquals("[['''1''' '''2'''] ['''4''' '''5''']]", clojureCollectionCollections);
        final String clojureCollectionColumn = ClojureInterpreter.toClojureCollectionString(new String[][]{{"1"},{"2"},{"3"}});
        Assert.assertEquals("['''1''' '''2''' '''3''']", clojureCollectionColumn);
        final String clojureCollectionSingleNumber = ClojureInterpreter.toClojureCollectionString(new String[][]{{"1"}});
        Assert.assertEquals("['''1''']", clojureCollectionSingleNumber);
        final String clojureCollectionStrings = ClojureInterpreter.toClojureCollectionString(new String[][]{{"a", "b", "3"}});
        Assert.assertEquals("['''a''' '''b''' '''3''']", clojureCollectionStrings);
    }

    public void testClojureCollectionStringToJavaLists(){
        assertClojureListToLists("[1 2 3]", "{{1 2 3 } }");
        assertClojureListToLists("[[1 2 3] [4 5 6]]", "{{1 2 3 } {4 5 6 } }");
    }
   
    private void assertClojureListToLists(final String clojureCollection, final String expected) {
        String asString = listsToString(ClojureInterpreter.fromClojureCollectionStringToList(clojureCollection));
        Assert.assertEquals(expected, asString);
    }

    private String listsToString(List<List<String>> lists) {
        String asString = "{";
        for (List<String> list : lists) {
            asString += "{";
            for (String string : list) {
                asString += string+" ";
            }
            asString += "} ";
        }
        asString += "}";
        return asString;
    }
    
    private void runAndAssert(final String exp, final String expected) {
        final String result = ClojureInterpreter.runClojure(exp);
        Assert.assertEquals(expected, result);
    }
}
