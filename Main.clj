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
