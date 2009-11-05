package networkController;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import red_gsm.MensajeGSM;

public class NetworkController extends Thread {

    private int duration = 8000;
    private HashMap timersTR = new HashMap();
    private static final long sleepTime = 1;
    private BlockingQueue<HeartbeatMessege> entrada;
    private BlockingQueue<MensajeGSM> entradaRaise;

    public NetworkController(int trCount) {
        String name;
        for (int i = 0; i < trCount; i++) {
            name = String.valueOf(i);
            TimerTR timer = new TimerTR(name, duration, this);
            timersTR.put(name, timer);
        }

    }

    public void setEntrada(BlockingQueue<HeartbeatMessege> entrada) {
        this.entrada = entrada;
    }

    public void setEntradaRaise(BlockingQueue<MensajeGSM> entradaRaise) {
        this.entradaRaise = entradaRaise;
    }

    public void recibirMensaje(HeartbeatMessege m) {
        try {
            String tr = m.getTrName().toString();

            TimerTR timerTR = (TimerTR) timersTR.get(tr);

            if (!timerTR.getTimerTRFall()) {
                if (timerTR.estaCorriendo()) {
                    System.out.println("NC      Restart: " + timerTR.getTrName());
                    timerTR.setIntervalo(duration);
                    timerTR.restart();
                } else {
                    System.out.println("NC    Init: " + timerTR.getTrName());
                    timerTR.setIntervalo(duration);
                    timerTR.start();
                }
            } else {
                System.out.println("NC    recup: " + timerTR.getTrName());
                timerTR.setIntervalo(duration);
                timerTR.restart();
            }
        } catch (Exception e) {
            System.out.println("NC No se pudo manejar el mensaje: " + m.toString());
        }
    }

    public void trRecuperada(String trName) {
        TimerTR timerTR = (TimerTR) timersTR.get(trName);
        if (timerTR.getTimerTRFall()) {
            // AVISAR A QUIEN CORRESPONDA QUE LA TR SE RECUPERO
            System.out.println("NC      Recuperacion de TR: " + timerTR.getTrName());
            timerTR.setIntervalo(duration);
            timerTR.setTimerTRFall(false);
            timerTR.run();
        }
    }

    @Override
    public void run() {
        while (true) {
             
            recibirMensaje();

        }
    }

    private boolean recibirMensaje() {
        
        //if (levanto != null) {
        if (entradaRaise.size() > 0) {
            MensajeGSM levanto = entradaRaise.poll();

            System.out.println("NC Se recibio mensaje RAISE de la TR " + levanto.getOrigen());
            String[] mensaje = levanto.getMensaje().split("#");
            try {
                trRecuperada(mensaje[1]);
            } catch (Exception e) {
                System.out.println("NC No se pudo avisar que la TR: " + levanto.getOrigen() + " se levanto");
            }
            return true;
        } else {
            HeartbeatMessege cabeza = entrada.poll();
            
            if (cabeza != null) {
                System.out.println("NC Se recibio mensaje HEART de la TR " + cabeza.getTrName());
                System.out.println("NC    entrada size: " + entrada.size());

                recibirMensaje(cabeza);
                return true;
            }
            return false;
        }
    }

    public void timeout(TimerTR t) {
        // AVISAR A QUIEN CORRESPONDA QUE LA TR ESTA CAIDA
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
