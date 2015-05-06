ClojureCalc
====================
ClojureCalc is a wrapper for clojure for libre office and possibly open office.  
It's highly experimental.  
To install use [ClojureCalc.oxt](https://github.com/beothorn/ClojureCalc/releases/download/0.0.1/ClojureCalc.oxt)  

Usage  
====================
Here's a screenshot to prove it works on my machine :)  
![screenshot](http://i.imgur.com/ydWloye.png "Example")

Examples  
====================

fibonacci  
<pre><code>
=clj("(def fib-seq ((fn rfib [a b] (lazy-seq (cons a (rfib b (+ a b))))) 0 1)) (take 20 fib-seq)")  
</code></pre>

sum of two cells  
<pre><code>
=clj("[(+ "&A1&" "&B1&")]")  
</code></pre>

escaping double quotes  
<pre><code>
=clj("(str "&CHAR(34)&"foo"&CHAR(34)&")")
</code></pre>

