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


;;;;;

(ns estsss.core)

(defn fifthpower
  [Num]
  (* Num Num Num Num Num))

(defn raisetofifth
  [Vec]
  (map fifthpower Vec)
  )

(defn raisetofifthandadd
  [Vec]
  (apply + (raisetofifth Vec))
 )

(defn Mean 
  [Vec]
  ( / (reduce #(+ %1 %2) Vec) (count Vec)
  ))

(defn Testor 
  []
 (fifthpower 4)
 (raisetofifth [123 123 1234 53 2])
 (raisetofifthandadd [123 123 1234 53 2])
 (Mean [1 2 3 4 5 6 7])
 )

(Testor)
