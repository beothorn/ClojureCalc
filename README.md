ClojureCalc
====================
![screenshot](http://i.imgur.com/s0ySCwL.png "ClojureCalc")

Combine the power of clojure with all the LibreOffice calc features!!!  
---------------------

ClojureCalc is a wrapper for clojure 1.6 on libre office and possibly open office.  

[Download v3.1.0](https://github.com/beothorn/ClojureCalc/releases/download/3.1.0/ClojureCalc.oxt)
====================

![screenshot](http://i.imgur.com/JeGaBs4.png "ClojureCalc")

Usage  
====================

Functions available  
--------------------

cljEval([expression];{[cell...]|[cell range ...]})  
cljs([expression];{[cell...]|[cell range ...]})  
cljn([expression];{[cell...]|[cell range ...]})  
cljToRange([expression];[cell text];[cell range])  
strfmt([string];[cell ...];[cell range])   


A simple instruction  
---------------------

To run a clojure expression just call cljeval  
Example:  
Sum of two numbers  
=CLJEVAL("(+ 1 2)")  
or with arguments  
=CLJEVAL("(+ {0} {1})";A1;A2)  

Using values from other cells or cell range
---------------------

Cells can be interpreted as strings or numbers.  
If you want the cells to be interpeted as strings use cljs, or if you need it to be interpreted as numbers use cljn.  
Cljs and Cljn automatically wraps the expresion with parenthesis.  
Cells and cell ranges are replaced on the string using a map formatter.  
If you need to add a String literal, use ''' in place of " .  
Examples:  
Sum of three cells:  
=CLJN("+ {0} {1} {3}";A1;B1;C1)  

Using reduce on a cell range:  
=CLJN("reduce + {0}";A1:B1)  

Join cell range with underscore:  
=CLJS("reduce #(str %1 '''_''' %2) {0}";A1:C1)  

Write collection to cell range
---------------------

You can write the output of an expression to a cell range using cljToRange.  
CljToRange works with arrays and matrices  
Examples:  
Writing a collection to three cells A1, A2 and A3:  
=CLJTORANGE("[1 2 3]";"CellText";A1:C1)  

Writing to a cell matrix:  
=CLJTORANGE("[[1 2 3][4 5 6]]";"CellText";A1:C2)  

Using the value of a cell as the colletion:  
=CLJTORANGE(A6;"Another cell text";A1:C2)  

Formatting a string  
---------------------  
As a bonus you can format a string with arguments using strfmt:  
=strfmt("Hello {0}";A1)  


Common issues
---------------------
On installation you may get an error:  
*Extension Installation Could not create Java Implementation Loader*  
This can happen when libre office is using a JDK instead of a JRE or if your Java installation has problems.  
To fix this change the jvm to be used by libre office on __Tools>Options>Advanced__  
More about this error on:  
http://ask.libreoffice.org/en/question/5712/extension-installation-could-not-create-java-implementation-loader/
