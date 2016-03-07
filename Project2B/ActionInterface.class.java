package StatsAgain;

import java.util.*;

public class ActionInterface {
    HashMap<String,double[]> ArrayDict = new HashMap();
    boolean instance = true;
    double[] Data;
    String Current;
    Scanner Scan = new Scanner(System.in);
    StatResources Res = new StatResources();
    
    private void SetCurrent(String Key){
        ArrayDict.computeIfPresent(Key,(a,b) -> this.Data = b);
        this.Current = Key;
        Res.Set(this.Data);
    }
    
    private void AddSet(String[] Inp){
        this.Data = new double[Inp.length - 2];
        for (int i = 2;i <= Inp.length - 1;i++){
            this.Data[i - 2] = Double.parseDouble(Inp[i]);
        }
        
        Arrays.sort(Data);
        if (ArrayDict.get(Inp[1]) != null){
            System.out.println("Overwritting " + Inp[1]);
        }
        ArrayDict.put(Inp[1], this.Data);
        SetCurrent(Inp[1]);
    }
    
    private void RemoveSet(String Key){
        ArrayDict.put(Key,null);
        SetCurrent("Default");
    }
    
    void Run(){
        ArrayDict.put("Default",new double[]{1,2,3,4,5});
        SetCurrent("Default");
        String[] Inp;
        while (instance){
            System.out.print("Option... ");
            Inp = Scan.nextLine().split(" ");
            switch (Inp[0]) {
                case "add": {
                    AddSet(Inp);
                    break;
                }case "quit": {
                    instance = false;
                    break;
                }case "use": {
                    SetCurrent(Inp[1]);
                    break;
                }case "stdev": {
                    System.out.println(Res.StDev());
                    break;
                }case "sum": {
                     System.out.println(Res.sum());
                     break;
                }case "mean": {
                     System.out.println(Res.mean());
                     break;
                }case "median": {
                     System.out.println(Res.median());
                     break;
                }case "primes": {
                     System.out.println(Res.primes());
                     break;
                }case "summary": {
                    
                     ArrayDict.forEach((Key, value) -> {
                          if(Key.equals(this.Current)) {
                              System.out.print("*    ");
                          }
                            System.out.println(Key + " : " + Arrays.toString(value));
                        });
                     
                     break;
                }default: {
                    System.out.println("Command does not exist.");
                    break;
                }
            }
        }
    }
    
    
    
    ActionInterface(){
       
    }
    
    
}
