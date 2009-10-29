package networkController;

public class Main {

    public static void main(String[] args) {
        int trCount = 10;
        NetworkController net = new NetworkController(trCount);
        System.out.println("INICIO");

        HeartbeatMessege messege = new HeartbeatMessege();
        messege.setTrName("TR"+666);
        net.recibirMensaje(messege);
        
        for (int i = 0; i < 20; i++) {

            HeartbeatMessege m = HeartbeatMessegeFactory.getInstance().createMessege();
            net.recibirMensaje(m);
            if(i == 19 ){
                net.trRecuperada("TR"+666);
            }
        }
       System.out.println("FIN");

        
    }

}
