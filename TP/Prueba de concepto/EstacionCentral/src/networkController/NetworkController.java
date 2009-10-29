package networkController;

import java.util.HashMap;

public class NetworkController{

    private HashMap timersTR = new HashMap();
    private int duration = 1;

    NetworkController(int trCount){
        String name;
        for (int i = 0; i < trCount; i++) {
            name = "TR"+i;
            TimerTR timer = new TimerTR(name, duration, this );
            timersTR.put(name,timer);
        }

        // sacar
        name = "TR"+666;
        TimerTR timer = new TimerTR(name, duration, this );
        timersTR.put(name,timer);
        //end sacar
        
    }
    public void recibirMensaje(HeartbeatMessege m){
        String tr = m.getTrName();
        TimerTR timerTR = (TimerTR)timersTR.get(tr);
        // Como el generador de mensajes no tiene en cuenta que la TR este caida
        // se verifica antes de tener encuenta la información.
        // Si la TR no esta Activa se deshecha el mensaje.
        if(!timerTR.getTimerTRFall())
            
            if( timerTR.estaCorriendo() ){
                // No es la primera vez que se recibe un mensaje de esta TR
                System.out.println("      R: " + timerTR.getTrName());
                timerTR.setIntervalo(duration);
            }else {
                // Es la primera vez que se recibe un mensaje de esta TR
                System.out.println("    I: " + timerTR.getTrName());
                timerTR.start();
            }
    }

    public void trRecuperada(String trName){
        TimerTR timerTR = (TimerTR)timersTR.get(trName);
        if( timerTR.getTimerTRFall() ){
           // AVISAR A QUIEN CORRESPONDA QUE LA TR SE RECUPERO
           System.out.println("      Recu: " + timerTR.getTrName());
           timerTR.setIntervalo(duration);
           timerTR.run();

        }
    }
    public void timeout( TimerTR t ) {
        // AVISAR A QUIEN CORRESPONDA QUE LA TR ESTA CAIDA
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
