(ns computerscience.project1b)
(require '[clojure.string :as str])

(def Stats {:default [1 2 3 4 5]})
(def Stats (read-string (slurp "StatCapture.txt")))
(def CurrentStat "default")

(defn Save
  [Map]
  (spit "StatCapture.txt" Map)
  )

(defn write-empty-dataset
  []
  (Save {:default [1 2 3 4 5]})
  )

;;

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

(defn BreakString
  [String]
  (str/split String #"\s"))

(defn Mean
  [Stat]
  (/ (reduce + Stat) (count Stat)))

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

(defn Quar
  [Set,Num]
  (if (= Num 1)
    (Mean (nth (split-at (- (/ (count Set) 2) 1) Set) 0))
    (Mean (nth (split-at (double (/ (count Set) 2)) Set) 1))))

(defn five
  [Stat]
  (let [Sor (sort Stat)]
  [(first Sor) (Quar Sor 1) (Median Sor) (Quar Sor 3) (last Sor)]))

(defn SetAsStat
  [String]
  (def CurrentStat (keyword String))
  (println "Using" CurrentStat "as dataset."))

(defn DefineStat
  [Name,Data]
  (let [Data (map #(read-string %1) Data)]
    (def Stats (assoc Stats (keyword Name) (vec Data)))
    (SetAsStat Name)
    (println "Stat added :" Name ", " Data)))

(defn remove-set
  [set]
  (if (not= set "default")
    ((def Stats (dissoc Stats (keyword set))) (if (= (keyword set) CurrentStat) 
                                                (SetAsStat "default"))) 
    (println "Can't remove default set.")))

(defn summary
  []
  (println "Summary of all datasets: ")
   (doseq [x (map-indexed #(nth %2 0) Stats)]
     
       (println "    " 
                (if (= x (keyword CurrentStat)) "*" "")
                x (get Stats x) (five (get Stats x)))))

(defn use
  [s]
  (if (nil? (get Stats (keyword s))) (println "No such dataset.") 
    (SetAsStat s)))

(defn KeyReader
  [UIn]
  (let [Stat (get Stats (keyword CurrentStat))]
    (case (str/lower-case (read-string UIn))
      "help" (Help)
      "mean" (println "Mean :" (Mean Stat))
      "median" (println "Median :" (Median Stat))
      "sum" (println "Sum :" (#(reduce + Stat)))
      "print" (println "Stat :" Stat)
      "prints" (println "Sorted Stat :" (sort Stat))
      "stdev" (println "Standard Deviation :" (Stdev Stat))
      "five" (println (five Stat))
      "save" (Save Stats)
      "add" (let [S (rest (BreakString UIn))]
              (DefineStat (first S) (vec (rest S))))
      "remove" (remove-set (nth (BreakString UIn) 1))
      "use" (use (last (BreakString UIn)))
      "summary" (summary)
      (println "No such clause.")
     )
    ))
  
(defn Load
  []
  (println "Welcome to SSS [Simple Statistics System].")
  (Help)
  (doseq
    [next (take-while 
            #(and (not= %1 "quit") (not= %1 "null"))
            (repeatedly #(read-line)))]
    
    (if (not= (get Stats CurrentStat) "nil") 
      (KeyReader next) 
      (println "Set not valid."))))
(Load)
(println "End of stats run.")
