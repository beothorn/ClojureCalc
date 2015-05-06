Clojure Wrapper for libre office calc

Experimental

To install use [ClojureCalc.oxt](https://github.com/beothorn/ClojureCalc/releases/download/0.0.1/ClojureCalc.oxt)

Usage  

![screenshot](http://i.imgur.com/ydWloye.png "Example")

Examples  

fibonacci  
=clj("(def fib-seq ((fn rfib [a b] (lazy-seq (cons a (rfib b (+ a b))))) 0 1)) (take 20 fib-seq)")  

sum of two cells  
=clj("[(+ "&A1&" "&B1&")]")  

escaping double quotes  
=clj("(str "&CHAR(34)&"foo"&CHAR(34)&")")

