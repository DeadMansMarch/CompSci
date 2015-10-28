(ns tests.core)

#(%1 + %2 + %3)

#(/ (+ %1 %2) 2)

(defn just-odd
  [V]
  (apply list (filter odd? V)))

(defn some-odd
  [V less]
  (apply list (filter #(< (%1 %1) less) (filter odd? V))))

(defn length-strings
  [L]
  (reduce #(%1 + (count %2)) 0 L))

(defn wacky-add
  [VecOfVec]
  (reduce #(+ %1 (apply + %2)) 0 VecOfVec))

(ns test.core)

(defn all-pairs
  [LA LB]
  (for [x LA y LB] [x y]))

(defn in-order
  [[Ind,Str]]
  (map #(nth Str %1) Ind))

(in-order [[0 1 2] ["wow" "ok" "then"]])
