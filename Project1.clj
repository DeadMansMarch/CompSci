(ns project1.core)

(defn Help
  []
  (println "Options for stat set :")
  (println "    mean - Calculate the mean of the set.")
  (println "    median - Calculate the median of the set.")
  (println "    sum - Calculate the sum of the set.")
  (println "    print - Prints the set.")
  (println "    prints - Prints the set in a sorted order.")
  (println "    stdev - Calculate the standard deviation of the set.")
  (println "    help - Print the help menu.")
  (println "    five - Print the five number summary.")
  (println "    quit - End the program.")
  )

(defn Mean
  [Stat]
  (/ (reduce + Stat) (count Stat) )
  )

(defn Median
  [Stat]
  (/ (+ (count Stat) 1) 2)
  )

(defn Stdev
  [Stat]
  (let [mean (Mean Stat) 
        Top (reduce #(+ %1 (*(- %2 mean) (- %2 mean))) 0 Stat)
        Bottom (- (count Stat) 1)]
    (java.lang.Math/sqrt (/ Top Bottom))))

(defn KeyReader
  [UIn Stat]
  
  
  (case (clojure.string/lower-case (read-string UIn))
    "help" (Help)
    "mean" (println "Mean :" (Mean Stat))
    "median" (println "Median :" (Median Stat))
    "sum" (println "Sum :" (#(reduce + Stat)))
    "print" (println "Stat :" Stat)
    "prints" (println "Sorted Stat :" (sort Stat))
    "stdev" (println "Standard Deviation :" (Stdev Stat))
    "five" (println (first (sort Stat)) () (Median Stat) () (last (sort Stat)))
   (println "Not a valid command.")
  )
  
  true)
 
(defn Load 
  []
  (println "Welcome to SSS [Simple Statistics System].")
  (println "Please input a statistics set.")
  
  (def StatSet (read-string (read-line)))
  
  (Help)
  
  (take-while 
    #(if 
      (and (not= %1 "quit") (not= %1 "null"))
      (if (not= %1 "") (KeyReader %1 StatSet) "Not a valid command.") (println"Shutting down..."))
    
    (repeatedly #(read-line))))

(Load)
