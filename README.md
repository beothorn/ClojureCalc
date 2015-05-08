ClojureCalc
====================
Combine the power of clojure with all the LibreOffice calc features!!!  
---------------------

ClojureCalc is a wrapper for clojure 1.6 on libre office and possibly open office.  

[Download v1.1.0](https://github.com/beothorn/ClojureCalc/releases/download/1.1.0/ClojureCalc.oxt)
=============


Usage  
---------------------

=c( string with a clojure function, without wrapping parenthesis )  
=clj( string with a clojure expression )  
=cljcol( cell range to convert to a clojure collection )  

Note: Three single quotes are replaced by a double quote

![screenshot](http://i.imgur.com/ePwRK5O.png "Really cool example")

Examples  
---------------------

Defining a lambda on a cell
<pre><code>
A1 is #(* % 2)
=clj("(map "&A1&" [1 2 3])")
</code></pre>

Fibonacci ()
<pre><code>
=clj("(def fib-seq ((fn rfib [a b] (lazy-seq (cons a (rfib b (+ a b))))) 0 1)) (take 20 fib-seq)")  
</code></pre>

Sum two cells
<pre><code>
=clj("[(+ "&A1&" "&B1&")]")  
</code></pre>

Escaping double quotes
<pre><code>
=clj("(str '''foo''')")
</code></pre>

Common issues
---------------------
On installation you may get an error:  
*Extension Installation Could not create Java Implementation Loader*  
This can happen when libre office is using a JDK instead of a JRE or if your Java installation has problems.  
To fix this change the jvm to be used by libre office on __Tools>Options>Advanced__  
More about this error on:  
http://ask.libreoffice.org/en/question/5712/extension-installation-could-not-create-java-implementation-loader/
