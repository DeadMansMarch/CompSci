(ns testsss.core)

(defn Multiply
  [VC]
  (apply * VC)
  )

(defn Square 
  [Num]
  (* Num Num)
  )

(defn SquareVectors
  [Vector]
  (map Square Vector)
  
  )

(defn AddSquares
  [Vec]
  (apply + (SquareVectors Vec))
 )

(defn Discrim
  [Vec]
  (- (Square(nth Vec 1)) (* 4 (nth Vec 0) (nth Vec 2))))

(defn Quadratic
  [Vector]
  (println Vector)
  (let [
        Discrimanant (Discrim Vector)
        ]
    (let [Discrim (Math/sqrt Discrimanant)]
      [(/ (+ (* -1 (nth Vector 1)) Discrim) (* 2 (nth Vector 0))) (/ (- (* -1 (nth Vector 1)) Discrim) (* 2 (nth Vector 0)))]
      )
    ))

;;Extra credit :

(defn VectorQuadratic
  [VoV]
  (map Quadratic VoV)
  )

(Quadratic '([2 4 6] [3 8 12]))
