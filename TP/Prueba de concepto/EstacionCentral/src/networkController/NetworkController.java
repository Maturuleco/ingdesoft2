package networkController;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import red_gsm.MensajeGSM;

public class NetworkController extends Thread{

    private int duration = 1;
    private HashMap timersTR = new HashMap();
    private static final long sleepTime = 100;
    private BlockingQueue<HeartbeatMessege> entrada;
    private BlockingQueue<MensajeGSM> entradaRaise;
    
    public NetworkController(int trCount){
        String name;
        for (int i = 0; i < trCount; i++) {
            name = String.valueOf(i);
            TimerTR timer = new TimerTR(name, duration, this );
            timersTR.put(name,timer);
        }
 
    }
     public void setEntrada(BlockingQueue<HeartbeatMessege> entrada) {
        this.entrada = entrada;
    }

    public void setEntradaRaise(BlockingQueue<MensajeGSM> entradaRaise) {
        this.entradaRaise = entradaRaise;
    }
    
    
    public void recibirMensaje(HeartbeatMessege m){
        try{
        String tr = m.getTrName().toString();
        System.out.println("NC Se recibe mensaje de TR: "+tr);
        TimerTR timerTR = (TimerTR)timersTR.get(tr);

        // Como el generador de mensajes no tiene en cuenta que la TR este caida
        // se verifica antes de tener encuenta la información.
        // Si la TR no esta Activa se deshecha el mensaje.
        if(!timerTR.getTimerTRFall())
            if( timerTR.estaCorriendo() ){
                // No es la primera vez que se recibe un mensaje de esta TR
                System.out.println("NC      Restart: " + timerTR.getTrName());
                timerTR.setIntervalo(duration);
            }else {
                // Es la primera vez que se recibe un mensaje de esta TR
                System.out.println("NC    Init: " + timerTR.getTrName());
                timerTR.start();
            }
        } catch (Exception e){
            System.out.println("NC No se pudo manejar el mensaje: "+m.toString());
        }
    }

    public void trRecuperada(String trName){
        TimerTR timerTR = (TimerTR)timersTR.get(trName);
        System.out.println("NC TR " + timerTR.getTrName()+" caida? "+timerTR.getTimerTRFall());
        if( timerTR.getTimerTRFall() ){
           // AVISAR A QUIEN CORRESPONDA QUE LA TR SE RECUPERO
           System.out.println("NC      Recuperacion de TR: " + timerTR.getTrName());
           timerTR.setIntervalo(duration);
           timerTR.run();
        }
    }
     @Override
    public void run() {
        
        while (true) {
            if (! recibirMensaje() ) {
                try {
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }
     
    private boolean recibirMensaje() {
        MensajeGSM levanto = entradaRaise.poll();
        if (levanto != null) {
            System.out.println("NC Se recibio mensaje RAISE de la TR "+levanto.getOrigen());
            //System.out.println("NC mensaje recuperacion: "+levanto.getMensaje());
            String[] mensaje = levanto.getMensaje().split("#");
            try{
                trRecuperada(mensaje[1]);
            } catch (Exception e){
                System.out.println("NC No se pudo avisar que la TR: "+levanto.getOrigen()+" se levanto");
            }
            return true;
        }
        HeartbeatMessege cabeza = entrada.poll();
        if (cabeza != null) {
            System.out.println("NC recibe mensaje: "+cabeza.toString());
            recibirMensaje(cabeza);
            return true;
        }
        return false;
    }
     
    public void timeout( TimerTR t ) {
        System.out.println("NC TR: "+t.getTrName()+" caida");
        // AVISAR A QUIEN CORRESPONDA QUE LA TR ESTA CAIDA
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
