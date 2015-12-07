(ns test.core)

(defn Save
  [Map]
  (spit "StatCapture.txt" Map)
  )



(def Stats {:default [1 2 3 4 5]})

(def Stats (read-string (slurp "StatCapture.txt")))

(println Stats)

(def CurrentStat "default")

(defn Help
  []
  (println "Options for stat" CurrentStat ":")
  (println "    mean - Calculate the mean of the set.")
  (println "    median - Calculate the median of the set.")
  (println "    sum - Calculate the sum of the set.")
  (println "    print - Prints the set.")
  (println "    prints - Prints the set in a sorted order.")
  (println "    stdev - Calculate the standard deviation of the set.")
  (println "    help - Print the help menu.")
  (println "    five - Print the five number summary.")
  (println "    use ds - Change the current dataset to 'ds'.")
  (println "    remove ds - Remove dataset with name 'ds'.")
  (println "    save - Save the program.")
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
  (def CurrentStat (keyword String))
  (println "Using" CurrentStat "as dataset."))

(defn DefineStat
  [Name,Data]
  (let [Data (map #(read-string %1) Data)]
    (def Stats (assoc Stats Name (vec Data)))
    (SetAsStat Name)
    (println "Stat added :" Name ", " Data)))


(defn KeyReader
  [UIn]

  (let [Stat (get Stats CurrentStat)]
    (case (str/lower-case (read-string UIn))
      "help" (Help)
      "mean" (println "Mean :" (Mean Stat))
      "median" (println "Median :" (Median Stat))
      "sum" (println "Sum :" (#(reduce + Stat)))
      "print" (println "Stat :" Stat)
      "prints" (println "Sorted Stat :" (sort Stat))
      "stdev" (println "Standard Deviation :" (Stdev Stat))
      "five" (println (first (sort Stat)) () (Median Stat) () (last (sort Stat)))
      "save" (Save Stats)
      "add" (let [S (rest (BreakString UIn))]
              (DefineStat (first S) (vec (rest S))))
      
      "remove" (dissoc Stats (first (rest (BreakString UIn))))
      
      "use" (SetAsStat (last (BreakString UIn))) (println "Using stat" CurrentStat)
      
      "summary" (println Stats)
      (println "Not a valid option.")
     
     )
    ))
  


 
(defn Load
  []
  (println "Welcome to SSS [Simple Statistics System].")
  (println "Using default.")
  (Help)
  (doseq
    [next (take-while 
            #(and (not= %1 "quit") (not= %1 "null"))
            (repeatedly #(read-line)))]
    
    (if (not= (get Stats CurrentStat) "nil") 
      (KeyReader next) 
      (println "Set not valid."))))

(Load)
