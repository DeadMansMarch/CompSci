package StatsAgain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class StatResources {
    private double Data[];
    
    static void print(String S){
        System.out.println(S);
    }
    
    void ChangeData(double Data[]){
        this.Data = Data;
        Arrays.sort(Data);
    }
    
    double[] Diagnose(){
        System.out.println(primes());
        return new double[]{Sum(), mean(), median(), StDev(), Mode()};
    
    }
    
    double Sum(){
        double Sum = 0;
        
        for (double c: this.Data){
            Sum += c;
        }
        
        return Sum;
    } 
    
    double mean(){
        return (this.Sum() / this.Data.length);
    }
    
    double median(){
        double median;
        if (this.Data.length % 2 == 0){
            int T = (int) Math.ceil((Data.length + 1)/2.0);
            int B = (int) Math.floor((Data.length + 1)/2.0);
            median = (Data[T - 1] +  Data[B - 1]) / 2.0;
        }else{
            median = Data[(Data.length + 1) / 2];
        }

        return median;
    }
    
    double StDev(){
        double Std = 0;
        double M = mean();
        
        for (double I: this.Data){
            Std += Math.pow((I - M),2);
        }
        Std = Math.sqrt(Std / (Data.length - 1));
        
        return Std;
    }
    
    double Mode(){
        int great = 0;
        double Val = 0;
        HashMap<String,Integer> MP = new HashMap<String,Integer>();
        for (double I: this.Data){
            double Key = I;
            int V = 0;
            
            if (MP.get(Double.toString(I)) == null){
                MP.put(Double.toString(I),1);
                V = 1;
            }else{
                MP.put(Double.toString(I),MP.get(Double.toString(I)) + 1);
                V = MP.get(Double.toString(I));
            }
            
            if (V > great){
                great = V;
                Val = Key;
            }
        }
        
        return Val;
    }
    
    HashSet primes(){
        HashSet<Integer> Prime = new HashSet<>();
        for (double A: this.Data){
            boolean Pr = true;
            for(int i = 2; i<= A / 2; i++){
               if (A % i == 0){
                   Pr = false;
                   break;
               }
            }
           if (Pr) {
               Prime.add((int) A);
           }
        }
        return Prime;
    }
    

    StatResources(double Data[]){
        this.Data = Data;
        Arrays.sort(this.Data);
    }
}
