(ns computerscience.project1b
  (:import (javax.swing SwingUtilities JFrame JPanel JLabel JButton)
           (java.awt Graphics Font FlowLayout BorderLayout Color Graphics2D)))
(use 'clojure.java.io)
(require '[clojure.string :as str])
(def CurrentStat "default")
(def GraphicsUse [])
(defn Save
  [Map]
  (spit "StatCapture.txt" Map)
  )
(if (not (.exists (as-file "StatCapture.txt")))((Save {:default [1 2 3 4 5]})))
(def Stats (read-string (slurp "StatCapture.txt")))
(defn Help
  []
  (println "Options for stat" CurrentStat ":")
  (println "    help - Print the help menu.")
  (println "    use ds - Change the current dataset to 'ds'.")
  (println "    remove ds - Remove dataset with name 'ds'.")
  (println "    save - Save the program.")
  (println "    boxplots ds ds - Graph the boxplots of all datasets 'ds'")
  (println "    scatterplots ds - Graph the scatterplots of all datasets 'ds'")
  (println "    quit - End the program.")
  )

(defn BreakString
  [String]
  (str/split String #"\s"))

(defn Median
  [Stat]
  (if odd? (count Stat) (nth (sort Stat) (/ (+ (count Stat) 1) 2))) 
  (let 
    [S (nth (sort Stat) (/ (+ (count Stat) 1) 2))]
    (Mean [(- S 2) (- S 1)])))

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
(defn Use
  [s]
  (if (nil? (get Stats (keyword s))) (println "No such dataset.") 
    (SetAsStat s)))

(defn box
  [g panel [Data SX SY]]
  (.drawString g "BoxPlots :" 10 20)
  (doseq [key Data]
    (def BoxIter (inc BoxIter))
    (let [name (get key 0) 
          dat (get key 1) 
          yPos (+ (* 80 ( - BoxIter 1)) 75) 
          fived (five dat)]
      (.drawLine g 20 yPos (- SX 20) yPos)
      (.drawLine g 20 (+ yPos 10) 20 (- yPos 10))
      (.drawLine g (- SX 20) (+ yPos 10) (- SX 20) (- yPos 10))
      (.drawString g (str name)(int (- (/ SX 2) 25)) (int (+ yPos 20)))
      (.drawString g (str (nth fived 0)) 17 (int (+ yPos 25)))
      (.drawString g (str (nth fived 4)) (int (- SX 23)) (int (+ yPos 25)))
      (let [val (double (/ (- (get fived 4) (get fived 0)) 10))
            FullScreen (- SX 40)
            
            GetPP (fn [Number] 
                    
                    (let [NoN (- (get fived 4) (get fived 0))
                          Percentage (/ (- Number (get fived 0)) NoN)]
                      (int (+ (* FullScreen Percentage) 20))
                      )
                    ) 
            ]
        (doseq [x (range 9)]
          
          (let [x (+ x 1)
                num (float (+ (int (get fived 0)) (* x val)))
                Pos  (GetPP num)]
            (.drawString g (str num) (- Pos (* 3 (count (str Pos)))) 
              (+ yPos 40))
            (.drawLine g Pos (+ yPos 10) Pos (- yPos 10))
            (.drawLine g 20 (- yPos 20) 20 (- yPos 40))
            ))
        
        (.drawLine g (- SX 20) (- yPos 20) (- SX 20) (- yPos 40))
        (.drawLine g (GetPP (get fived 1)) (- yPos 25) 
          (GetPP (get fived 3)) (- yPos 25))
        (.drawLine g (GetPP (get fived 1)) (- yPos 35) 
          (GetPP (get fived 3)) (- yPos 35))
        (.drawLine g (GetPP (get fived 1)) (- yPos 35) 
          (GetPP (get fived 1)) (- yPos 25))
        (.drawLine g (GetPP (get fived 3)) (- yPos 35) 
          (GetPP (get fived 3)) (- yPos 25))
        (.drawLine g (GetPP (get fived 0)) (- yPos 30) 
          (GetPP (get fived 1)) (- yPos 30))
        (.drawLine g (GetPP (get fived 3)) (- yPos 30) 
          (GetPP (get fived 4)) (- yPos 30))
        (.drawLine g (GetPP (get fived 2)) (- yPos 35) 
          (GetPP (get fived 2)) (- yPos 25))
        )
      
      ))
  
  )

(defn scatter
  [g panel [Data SX SY]]
  (.drawString g "ScatterPlots :" 10 20)
  (doseq [key Data]
    (def BoxIter (inc BoxIter))
    (let [name (get key 0)
          dat (get key 1)
          x1 (if (odd? BoxIter) 30 (int (+ (/ SX 2) 10)))
          x2 (if (odd? BoxIter) (int (- (/ SX 2) 30)) (int (- SX 10)))
          y1 (if (odd? BoxIter) 30 (int (+ (/ SY 2) 10)))
          y2 (if (odd? BoxIter) (int (- (/ SY 2) 30))(int (- SY 60))) 
          fived (five dat)
          ]
      (declare ^:dynamic pSet)
      (declare ^:dynamic Tot)
      
      (binding [pSet {} Tot 0]
        (doseq [Num dat]
          (if (not (nil?(get pSet Num))) 
            (set! pSet (assoc pSet Num (inc (get pSet Num))))
            (set! pSet (assoc pSet Num 1))
            )
          (if (> (get pSet Num) Tot) (set! Tot (get pSet Num)))
          )
        (def pSet pSet) (def Tot Tot))
      
      (.drawLine g x1 y2 x2 y2)
      
      (.drawLine g x2 (+ y2 5) x2 (- y2 5))
      (.drawLine g x1 y1 x1 y2)
      (.drawString g (str name) (int (- (/ x2 2) 25)) 
        (int (+ y2 20)))
      
      (let [val (double (/ (- (get fived 4) (get fived 0)) 10))
            FullScreen (- x2 x1)
            
            GetPx (fn [Number] 
                    
                    (let [NoN (- (get fived 4) (get fived 0))
                          Percentage (/ (- Number (get fived 0)) NoN)]
                      (int (+ (* FullScreen Percentage) x1))
                      )
                    ) 
            
            GetPy (fn [Number] 
                    
                    (let [NoN Tot Percentage (/ Number NoN)]
                      (int (- y2 (* (- y2 y1) Percentage)))
                      )
                    ) 
            ]
        (doseq [[x y] pSet]
          (let [Pos  (GetPx x)]
            (.drawString g (str x) (- Pos (* 2 (count (str Pos)))) (+ y2 40))
            (.drawLine g Pos (+ y2 5) Pos (- y2 5))
            
            (.setColor g Color/white)
            (.fillRect g (- (GetPx x) 2), (- (GetPy y) 2), 4, 4)
            (.setColor g Color/red)
            
            ))
        (doseq [x (rest (range (inc Tot)))]
          (.drawLine g (- x1 3) (GetPy x) (+ x1 3) (GetPy x))
          (.drawString g (str x) (- x1 13) (+ (GetPy x) 5))
          )
        )
      ))
  )

(defn do-paint
  [g panel]
  
  (let [SX (.getWidth (.size panel))
        SY (.getHeight (.size panel))
        DoPlot (nth GraphicsUse 1)
        Type (nth GraphicsUse 0)
        Data (for [x DoPlot] [x (get Stats (keyword x))])
        Number (count Data)]
    (.clearRect g 0 0 SX SY)
    (.setColor g Color/black)
    (.fillRect g 5 5 (- SX 10) (- SY 10))
    (.setColor g Color/red)
    (def BoxIter 0)
    (case Type
      "box" (box g panel [Data SX SY])
      "scatter" (scatter g panel [Data SX SY])
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

(defn boxplot
  [DataSets]
  (def GraphicsUse ["box" DataSets])
  (let [frame (makeGui)]
    (.setTitle frame "Box Plot")
    )
  )

(defn scatterplot
  [DataSets]
  (def GraphicsUse ["scatter" DataSets])
  (let [frame (makeGui)]
    (.setTitle frame "Scatter Plot")
    )
  )

(defn KeyReader
  [UIn] ;I removed some of the inputs to save space.
  (let [Stat (get Stats (keyword CurrentStat))]
    (case (str/lower-case (read-string UIn))
      "help" (Help)
      "save" (Save Stats)
      "add" (let [S (rest (BreakString UIn))]
              (DefineStat (first S) (vec (rest S))))
      "remove" (remove-set (nth (BreakString UIn) 1))
      "use" (Use (last (BreakString UIn)))
      "summary" (summary)
      "boxplots" (boxplot (vec (rest (BreakString UIn))))
      "scatterplots" (scatterplot (vec (rest (BreakString UIn))))
      (println "No such clause.")
      )
    ))

(defn Load
  []
  (println "Welcome to SSSGrapher.")
  (Help)
  (doseq
    [next (take-while 
            #(and (not= %1 "quit") (not (nil? next)) (not= next ""))
            (repeatedly #(read-line)))]
    
    (if (not (nil? (get Stats (keyword CurrentStat))))
      (KeyReader next) 
      (println "Set not valid."))))
(Load)

(println "End of stats run.")
