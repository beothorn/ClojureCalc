package com.github.beothorn.clojurecalc;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.LazySeq;
import java.util.Iterator;

public class ClojureInterpreter {
    
    public static String toClojureCollectionString(String[][] javaMatrix){
        return toClojureCollection(javaMatrix, "'''", "''''''");
    }
    
    public static String toClojureCollectionNumber(String[][] javaMatrix){
        return toClojureCollection(javaMatrix, "", "0");
    }

    private static String toClojureCollection(String[][] javaMatrix, String container, String onEmptyValue) {
        if(javaMatrix.length == 1 && javaMatrix[0].length == 1){
            return "["+container+javaMatrix[0][0]+container+"]";
        }
        if(javaMatrix[0].length == 1){
            String result = "[";
            for (int i = 0; i < javaMatrix.length; i++) {
                final String cellValue = javaMatrix[i][0];
                if(cellValue.equals("")){
                    result += onEmptyValue+" ";
                }else{
                    result += container+cellValue+container+" ";
                }
            }
            result = result.substring(0, result.length() - 1);
            result += "]";
            return result;
        }
        if(javaMatrix.length <= 1){            
            return getLine(javaMatrix[0], container, onEmptyValue);
        }else{
            String result = "[";
            for (int i = 0; i < javaMatrix.length; i++) {
                result += getLine(javaMatrix[i], container, onEmptyValue)+" ";
            }
            result = (result.length()>1)?result.substring(0, result.length() - 1):result;
            result += "]";
            return result;
        }
    }

    private static String getLine(final String[] line, String container, String onEmptyValue) {
        String result = "[";
        for (int i = 0; i < line.length; i++) {
            final String cellValue = line[i];
            if(cellValue.equals("")){
                result += onEmptyValue+" ";
            }else{
                result += container+cellValue+container+" ";
            }
        }
        result = result.substring(0, result.length() - 1);
        result += "]";
        return result;
    }
    
    public static String runClojure(String clojureExpression){
        if(clojureExpression.trim().equals("")) return "";
        String exp = clojureExpression.replaceAll("'''", "\"");
        String result;
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        final ClassLoader parentClassLoader = ClojureCalcImpl.class.getClassLoader();
        Thread.currentThread().setContextClassLoader(parentClassLoader);
        try {
            result = evalResultToString(exp);
        }catch(Exception e){  
            result = e.getMessage();
        } finally {
            Thread.currentThread().setContextClassLoader(previous);
        }
       
        return result;
    }

    private static String evalResultToString(String exp) {
        String result;
        final IFn eval = Clojure.var("clojure.core", "load-string");
        Object evalResult = eval.invoke(exp);
        IFn str = Clojure.var("clojure.core", "str");
        if(evalResult instanceof LazySeq){
            LazySeq resultCollection = (LazySeq) evalResult;
            result = "[";
            for (Iterator it = resultCollection.iterator(); it.hasNext();) {
                Object object = it.next();
                result += str.invoke(object).toString()+" ";
            }
            result = result.substring(0, result.length() - 1);
            result += "]";
        }else{
            result = str.invoke(evalResult).toString();
        }
        return result;
    }
}
