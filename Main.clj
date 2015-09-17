
(ns testing.core)

(defn Exp
  [X]
  (* X X)
  )

(defn Comp-Area
  [Base Hight]
  (/ (* Base Hight),2)
  )

(defn DistanceForm
  [X,Y,A,B]
  
  (Math/sqrt (+ (Exp (- X A)) (Exp (- Y B))))
  )

(defn SqrCube
  [Int]
  (if (even? Int) (* Int Int) (* Int Int Int))
  
  )

(defn GetDiscrim
  [a b c]
  
  (- (Exp b) (* 4 a c))
  
  )

(defn GetRealRoots
  [X,Y,Z]
  (let [X,(GetDiscrim X Y Z)])
  (if (= X 0) (println "One [Real]")(if (> X 0) (println "Two [Real]") (if (< X 0) (println "2 [Complex]"))))
  )

(GetRealRoots -4 10 0)
