package com.github.beothorn.clojurecalc;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.LazySeq;
import java.util.Iterator;

public class ClojureInterpreter {
    public static String runClojure(String exp){
        if(exp.equals("")) return "";
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
