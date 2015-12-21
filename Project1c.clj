(ns computerscience.project1b
(:import (javax.swing SwingUtilities JFrame JPanel JLabel JButton)
         (java.awt Graphics Font FlowLayout BorderLayout Color Graphics2D)))

(require '[clojure.string :as str])

(def Stats {:default [1 2 3 4 5]})
(def Stats (read-string (slurp "StatCapture.txt")))
(def CurrentStat "default")
(def GraphicsUse [])


(defn Save
  [Map]
  (spit "StatCapture.txt" Map)
  )

(defn write-empty-dataset
  []
  (Save {:default [1 2 3 4 5]})
  )

;;(write-empty-dataset)

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

;;boxplot functions

(defn five
  [Stat]
  (let [Sor (sort Stat)]
  [(first Sor) (Quar Sor 1) (Median Sor) (Quar Sor 3) (last Sor)]))

(defn do-paint
  [g panel]
  
  (let [SX (.getWidth (.size panel))
        HalfX (/ SX 2)
        SY (.getHeight (.size panel))
        DoPlot (nth GraphicsUse 1)
        Type (nth GraphicsUse 0)
        Data (for [x DoPlot] [x (get Stats (keyword x))])
        Number (count Data)
        ]
    (.clearRect g 0 0 SX SY)
    (.setColor g Color/black)
    (.fillRect g 5 5 (- SX 10) (- SY 10))
    
    (.setColor g Color/red)
    (.drawString g "BoxPlots :" 10 20)
    (def BoxIter 0)
    (case Type
      "box" (do
              (doseq [key Data]
                (def BoxIter (inc BoxIter))
                (let [name (get key 0) dat (get key 1) yPos (+ (* 80 ( - BoxIter 1)) 75) fived (five dat)]
                  (.drawLine g 20 yPos (- SX 20) yPos)
                  (.drawLine g 20 (+ yPos 10) 20 (- yPos 10))
                  (.drawLine g (- SX 20) (+ yPos 10) (- SX 20) (- yPos 10))
                  (.drawString g (str name) (int (- HalfX 25)) (int (+ yPos 20)))
                  (.drawString g (str (nth fived 0)) 17 (int (+ yPos 25)))
                  (.drawString g (str (nth fived 4)) (int (- SX 23)) (int (+ yPos 25)))
                  (println name dat)
                  (let [val (double (/ (- (get fived 4) (get fived 0)) 10))
                        FullScreen (- SX 40)
                        k (println FullScreen)
               
                        GetPP (fn [Number] (- (* Number (/ FullScreen (get fived 4))) (/ FullScreen 10))) 
                        k (println (GetPP 1))
                        ]
                      (doseq [x (range 9)]
             
                        (let [x (+ x 1)
                              num (float (+ (int (get fived 0)) (* x val)))
                              TensQuad (/ FullScreen 10)
                              Pos (int (+ (* TensQuad x) 20))
                              ]
                          (println Pos (GetPP 1) (GetPP (get fived 1)) TensQuad)
                          (.drawString g (str num) Pos (+ yPos 40))
                          (.drawLine g Pos (+ yPos 10) Pos (- yPos 10))
                          (.drawLine g 20 (- yPos 20) 20 (- yPos 40))
                          (.drawLine g (- SX 20) (- yPos 20) (- SX 20) (- yPos 40))
                          (.drawLine g (GetPP (get fived 1)) (- yPos 25) (GetPP (get fived 3)) (- yPos 25))
                          (.drawLine g (GetPP (get fived 1)) (- yPos 35) (GetPP (get fived 3)) (- yPos 35))
                          (.drawLine g (GetPP (get fived 1)) (- yPos 35) (GetPP (get fived 1)) (- yPos 25))
                          (.drawLine g (GetPP (get fived 3)) (- yPos 35) (GetPP (get fived 3)) (- yPos 25))
                          (.drawLine g (GetPP (get fived 0)) (- yPos 30) (GetPP (get fived 1)) (- yPos 30))
                          (.drawLine g (GetPP (get fived 3)) (- yPos 30) (GetPP (get fived 4)) (- yPos 30))
                        ))
                    )
                  
                  ))
              
              )
      "scatter" (do
                 
                  )
      :default)
    ))

(defn make-panel
  []
  (proxy [JPanel][]
    (paint [g]
      (do-paint g this))))

(defn drawing-panel
  [panel]
  (doto panel
    (.setSize 600 300)))

(defn makeGui
  []
  (let [F (JFrame.)
        panel (make-panel)]
    (doto F
      (.setLayout (BorderLayout.))
      (.setSize 640 480)
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.add (drawing-panel panel) "Center")
      (.setVisible true)
      )))

;;EndGraphics

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

(defn Use
  [s]
  (if (nil? (get Stats (keyword s))) (println "No such dataset.") 
    (SetAsStat s)))

(defn boxplot
  [DataSets]
  (def GraphicsUse ["box" DataSets])
  (let [frame (makeGui)]
    (.setTitle frame "Box Plot")
    )
  )

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
      "use" (Use (last (BreakString UIn)))
      "summary" (summary)
      "boxplots" (boxplot (vec (rest (BreakString UIn))))
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
