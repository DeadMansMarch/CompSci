(ns project1.core)

(def Stats {})
(def CurrentStat "none")

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
  (println "    addstat - Add a statistic to the program's memory.")
  (println "    setstat - Set the current oparative statistic.")
  (println "    showstat - Show a list of all known stats.")
  (println "    quit - End the program."))

(defn Mean
  [Stat]
  (/ (reduce + Stat) (count Stat) ))

(defn Median
  [Stat]
  (/ (+ (count Stat) 1) 2))

(defn Stdev
  [Stat]
  (let [mean (Mean Stat) 
        Top (reduce #(+ %1 (*(- %2 mean) (- %2 mean))) 0 Stat)
        Bottom (- (count Stat) 1)]
    (java.lang.Math/sqrt (/ Top Bottom))))

(defn SetAsStat
  [String]
  (def CurrentStat (keyword String)))

(defn DefineStat
  []
  (println "Input a data name then a data set.") 
  (let [Name (keyword (read-line)) Set (read-string (read-line))]
    (def Stats (assoc Stats Name Set)) 
    (SetAsStat Name)
    (println "Stat added!")))


(defn KeyReader
  [UIn]
  (let [Stat (get Stats CurrentStat)]
    (println "Using " CurrentStat " as statistic :")
    (case (clojure.string/lower-case (read-string UIn))
      "help" (Help)
      "mean" (println "Mean :" (Mean Stat))
      "median" (println "Median :" (Median Stat))
      "sum" (println "Sum :" (#(reduce + Stat)))
      "print" (println "Stat :" Stat)
      "prints" (println "Sorted Stat :" (sort Stat))
      "stdev" (println "Standard Deviation :" (Stdev Stat))
      "five" (println (first (sort Stat)) () (Median Stat) () (last (sort Stat)))
      "setstat" (let [NewStat (read-line)] (println "Setting stat as :" NewStat) (SetAsStat NewStat))
      "addstat" (DefineStat)
      "showstat" (println Stats)
     (println "Not a valid command."))
    ))
  


 
(defn Load
  []
  (println "Welcome to SSS [Simple Statistics System].")
  (DefineStat)
  (Help)
  (doseq
    [next (take-while 
            #(and (not= %1 "quit") (not= %1 "null"))
            (repeatedly #(read-line)))]
    
    (println (get Stats CurrentStat))
    (if (not= (get Stats CurrentStat) "nil") 
      (KeyReader next) 
      (println "Set not valid."))))

(Load)
