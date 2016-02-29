

package virtualmachine;

public class VirtualMachine {
    
    static void InitMachine(){
        char Read[] = {'1','0','0','0','r'};
        TurringReader Reader = new TurringReader(Read);
    }
    
    public static void main(String[] args) {
       VirtualMachine Turring = new VirtualMachine();
       Turring.InitMachine();
    }
    
}
