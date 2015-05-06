Clojure Wrapper for libre office calc

Usage  

=clj("(def fib-seq ((fn rfib [a b] (lazy-seq (cons a (rfib b (+ a b))))) 0 1)) (take 20 fib-seq)")

