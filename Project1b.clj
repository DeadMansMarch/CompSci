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
  (println "    quit - End the program."))
(require '[clojure.string :as str])

(defn BreakString
  [String]
  (str/split String #"\s")
  )

(defn Mean
  [Stat]
  (/ (reduce + Stat) (count Stat) ))

(defn Median
  [Stat]
  (if odd? (count Stat) (nth (sort Stat) (/ (+ (count Stat) 1) 2))) 
  (let 
    [S (nth (sort Stat) (/ (+ (count Stat) 1) 2))]
    (Mean [(- S 2) (- S 1)])))

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
  (println "Input a data name and a data set.")
  (let [String (BreakString (read-line)) Name (keyword (nth String 0)) Data (rest String)]
    (def Stats (assoc Stats Name (read-string Data)))
    (SetAsStat Name)
    (println "Stat added!")))


(defn KeyReader
  [UIn]
  (let [Stat (get Stats CurrentStat)]
    (case (clojure.string/lower-case (read-string UIn))
      "help" (Help)
      "mean" (println "Mean :" (Mean Stat))
      "median" (println "Median :" (Median Stat))
      "sum" (println "Sum :" (#(reduce + Stat)))
      "print" (println "Stat :" Stat)
      "prints" (println "Sorted Stat :" (sort Stat))
      "stdev" (println "Standard Deviation :" (Stdev Stat))
      "five" (println (first (sort Stat)) () (Median Stat) () (last (sort Stat)))
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
