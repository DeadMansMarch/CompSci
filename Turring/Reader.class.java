package turring;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.*;

public class Reader {
    HashMap<String,tuple> Tuples = new HashMap();
    int State = 0;
    String Input = "-111----------";
    private void Iterate(){
        int i = 0;
        while (true){
            tuple Current = Tuples.get(tuple.makeKey(State,Input.charAt(i)));
            if (Current == null){
                break;
            }
            State = Current.getNewState();
            System.out.println("Iteration : " + Integer.toString(i));
            String Input = this.Input.substring(0,i) 
                    + Current.getOutput() +
                    this.Input.substring(i + 1);
            this.Input = Input;
            if (Current.GetDirection() == 'l'){
                i -= 1;
            }else{
                i += 1;
            }
            
        }
       System.out.println(Input);
    }
    
    private HashMap Read(String File){
        Stream<String> fileInst = null;
        HashMap<String,tuple> Tuples = new HashMap();
        try{
            fileInst = Files.lines(Paths.get(File));
        }catch(Exception FileNotFound){
            System.out.println("Exception : " + FileNotFound.toString());
        }
        if (fileInst != null){
          
            String[] STArray = fileInst.toArray(String[]::new);

            for (String Tuple : STArray){
                String[] ItemSet = Tuple.split(" ");
                
                if (ItemSet.length != 5){
                    
                }else{
                    tuple Item = new tuple(Integer.parseInt(ItemSet[0]),
                            ItemSet[1].charAt(0),
                            Integer.parseInt(ItemSet[2]),
                            ItemSet[3].charAt(0),
                            ItemSet[4].charAt(0));
                    Tuples.put(Item.getKey(),Item);
                }
            }
        }
        
        return Tuples;
    }
    
    Reader(String FileName){
        this.Tuples = Read(FileName);
        Iterate();
    }
}
