package turring;

public class tuple {
    int State;
    char Input;
    int NewState;
    char output;
    char direction;
    
    char GetDirection(){
        return direction;
    }
    
    char getInput(){
        return Input;
    }
    
    String getKey(){
        return makeKey(State, Input);
    }
    
    int getNewState(){
        return NewState;
    }
    
    char getOutput(){
        return output;
    }
    
    int getState(){
        return State;
    }
    
    static String makeKey(int st,char inp){
        return Integer.toString(st) + inp;
    }
    
    @Override
    public String toString(){
        return "{" + Integer.toString(State) + " " + Input + " " + 
                Integer.toString(NewState) + " " 
                + output + " " + direction + "}";
    }

    tuple(int st, char inp, int newSt, char outp, char dir){
        State = st;
        Input = inp;
        NewState = newSt;
        output = outp;
        direction = dir;
    }
}
