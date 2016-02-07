
package StatsAgain;

public class Project2A {

    void doTest(){
        double Data[] = {3,2,4,4};
        StatResources F = new StatResources(Data);
        
        for(double C: F.Diagnose()){
            System.out.println(C);
        }
    }
    
    public static void main(String[] args) {
        Project2A c = new Project2A();
        c.doTest();
    }
    
}
