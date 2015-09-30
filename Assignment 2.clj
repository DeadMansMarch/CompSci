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
  [[a b c]]
  (- (Square b) (* 4 a c)))

(defn Quadratic
  [[a b c]]
  (let [
        D (Math/sqrt(Discrim [a b c]))
      
        ]
      [(/ (+ (* -1 b) D) (* 2 a)) (/ (- (* -1 b) D) (* 2 a))]
      )
    )

;;Extra credit :

(defn VectorQuadratic
  [VoV]
  (map Quadratic VoV)
  )

(VectorQuadratic [[1 5 1] [3 8 12]])
