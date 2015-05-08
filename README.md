ClojureCalc
====================
Combine the power of clojure with all the LibreOffice calc features!!!  

ClojureCalc is a wrapper for clojure 1.6 on libre office and possibly open office.  

To install use [ClojureCalc.oxt](https://github.com/beothorn/ClojureCalc/releases/download/1.1.0/ClojureCalc.oxt)  

Usage  
====================

=c( string with a clojure function, without wrapping parenthesis )
=clj( string with a clojure expression )  
=cljcol( cell range to convert to a clojure collection )  

Note: Three single quotes are replaced by a double quote

![screenshot](http://i.imgur.com/ePwRK5O.png "Really cool example")

Examples  
====================

defining functions
<pre><code>
A1 is #(* % 2)
=clj("(map "&A1&" [1 2 3])")
</code></pre>

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
=clj("(str '''foo''')")
</code></pre>

