package com.github.beothorn.clojurecalc;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.LazySeq;
import clojure.lang.PersistentVector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClojureInterpreter {
    
    public static String toClojureCollectionString(Object[][] javaMatrix){
        return toClojureCollection(javaMatrix, "'''", "''''''");
    }
    
    public static String toClojureCollectionNumber(Object[][] javaMatrix){
        return toClojureCollection(javaMatrix, "", "0");
    }

    private static String toClojureCollection(Object[][] javaMatrix, String container, String onEmptyValue) {
        if(javaMatrix.length == 1 && javaMatrix[0].length == 1){
            return "["+container+javaMatrix[0][0]+container+"]";
        }
        if(javaMatrix[0].length == 1){
            String result = "[";
            for (int i = 0; i < javaMatrix.length; i++) {
                final String cellValue = javaMatrix[i][0].toString();
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

    private static String getLine(final Object[] line, String container, String onEmptyValue) {
        String result = "[";
        for (int i = 0; i < line.length; i++) {
            final String cellValue = line[i].toString();
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
                if(object instanceof String){
                    result += "'''"+str.invoke(object).toString()+"''' ";
                }else{
                    result += str.invoke(object).toString()+" ";
                }
            }
            if(result.equals("[")){
                return "[]";
            }
            result = result.substring(0, result.length() - 1);
            result += "]";
        }else{
            result = str.invoke(evalResult).toString();
        }
        return result;
    }

    static List<List<String>> fromClojureCollectionStringToList(String cljColString) {
        ClassLoader previous = Thread.currentThread().getContextClassLoader();
        final ClassLoader parentClassLoader = ClojureCalcImpl.class.getClassLoader();
        Thread.currentThread().setContextClassLoader(parentClassLoader);
        try {
            return internalFromClojureCollectionStringToList(cljColString);
        }catch(Exception e){  
            List<List<String>> x = new ArrayList<List<String>>();
            final ArrayList<String> message = new ArrayList<String>();
            message.add(e.getMessage());
            x.add(message);
            return x;
        } finally {
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    private static List<List<String>> internalFromClojureCollectionStringToList(String cljColString) {
        String exp = cljColString.replaceAll("'''", "\"");
        final IFn eval = Clojure.var("clojure.core", "load-string");
        Object evalResult = eval.invoke(exp);
        IFn str = Clojure.var("clojure.core", "str");
        if(!(evalResult instanceof PersistentVector)){
            return new ArrayList<List<String>>();
        }
        
        final Object[] array = ((PersistentVector) evalResult).toArray();
        if(array.length == 0){
            return new ArrayList<List<String>>();
        }
        
        if(array[0] instanceof PersistentVector){ //we assume it's a list of lists
            final ArrayList<List<String>> collectionElements = new ArrayList<List<String>>();
            for (int i = 0; i < array.length; i++) {
                if(array[i] instanceof PersistentVector){
                    ArrayList<String> line = new ArrayList<String>();
                    final Object[] subArray = ((PersistentVector) array[i]).toArray();
                    for (int j = 0; j < subArray.length; j++) {
                        line.add(subArray[j].toString());
                    }
                    collectionElements.add(line);
                }else{
                    ArrayList<String> line = new ArrayList<String>();
                    line.add(array[i].toString());
                    collectionElements.add(line);
                }
            }

            return collectionElements;
        }else{
            final ArrayList<List<String>> collectionElements = new ArrayList<List<String>>();
            ArrayList<String> line = new ArrayList<String>();
            for (int i = 0; i < array.length; i++) {
                if(array[i] instanceof PersistentVector){
                    final Object[] subArray = ((PersistentVector) evalResult).toArray();

                }else{
                    line.add(array[i].toString());
                }
            }

            collectionElements.add(line);

            return collectionElements;
        }
    }
}
