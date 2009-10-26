package networkcontroller;

import java.util.Random;

public class HeartbeatMessegeFactory {

    private static HeartbeatMessegeFactory instance = new HeartbeatMessegeFactory();
    private int min = 0;
    private int max = 10;
    HeartbeatMessegeFactory(){ }

    public static HeartbeatMessegeFactory getInstance() {
        return instance;
    }

    public HeartbeatMessege createMessege(){
        // Genera un número de TR entre [min, max]
        int num =  min + (new Random()).nextInt(max-min);
        
        HeartbeatMessege messege = new HeartbeatMessege();
        messege.setTrName("TR"+num);
        return messege;
    }
    
}