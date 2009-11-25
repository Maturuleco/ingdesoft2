package networkController;

import model.HeartbeatMessege;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MensajeNetworkController;
import model.MensajeRaise;
import red_gsm.MensajeGSM;

public class NetworkController extends Thread {

    private static final long delay = 120000; // el tiempo esperado para decidir que una TR se cae, 2'
    
    private Timer timer = new Timer();
    private HashMap<Integer, TimerTaskTR> timersTR = new HashMap<Integer, TimerTaskTR>();
    private HashMap<Integer, Boolean> trCaidas = new HashMap<Integer, Boolean>();
    private HashMap<Integer, Long> trUltMensaje = new HashMap<Integer, Long>();

    private BlockingQueue<HeartbeatMessege> entrada;
    private BlockingQueue<MensajeGSM> entradaRaise;

    public void setEntrada(BlockingQueue<HeartbeatMessege> entrada) {
        this.entrada = entrada;
    }

    public void setEntradaRaise(BlockingQueue<MensajeGSM> entradaRaise) {
        this.entradaRaise = entradaRaise;
    }

    @Override
    public void run() {
        while (true) {
            recibirMensaje();
        }
    }

    private boolean recibirMensaje() {

        if (entradaRaise.size() > 0) {
            try {
                MensajeGSM levanto = entradaRaise.poll();
                String contenido = levanto.getMensaje().split("#")[2];
                MensajeRaise mensajeRaise = MensajeRaise.parse(contenido);
                System.out.println("NC Recibio mensaje RAISE de la TR " + mensajeRaise.getIdTR());
                procesarMensajeNC(mensajeRaise);
            } catch (ParseException ex) {
                Logger.getLogger(NetworkController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        } else if(entrada.size() > 0) {
            HeartbeatMessege mensajeHeartbeat = entrada.poll();
            System.out.println("NC Recibio mensaje HEART de la TR " + mensajeHeartbeat.getIdTR());
            procesarMensajeNC(mensajeHeartbeat);
            actualizarEstadoRedTelemetrica(mensajeHeartbeat);
            return true;
        }
        return false;
    }

    public synchronized void timeout(Integer nombreTR) {
        // AVISAR A QUIEN CORRESPONDA QUE LA TR ESTA CAIDA
        System.out.println("NC TR " + nombreTR + " caida");
        trCaidas.put(nombreTR, true);
    }

    private void procesarMensajeNC(MensajeNetworkController mensaje) {
        Integer nombreTR = mensaje.getIdTR();
        
        Long fechaUltMensajeRecibido = trUltMensaje.get(nombreTR);
        Long fechaMensajeRaise = mensaje.getFecha().getTime();

        // Si no es un mensaje viejo
        if( fechaUltMensajeRecibido == null || fechaUltMensajeRecibido < fechaMensajeRaise){
            trUltMensaje.put(nombreTR, fechaMensajeRaise);

            // Si es un mensaje de REGISTRO EN LA RED
            if( !timersTR.containsKey(nombreTR) ){
                registrarTR(nombreTR);
                iniciarMonitoreoDeTR(nombreTR);
            }
            startTimerTR(nombreTR);

        }
    }

    private void registrarTR(Integer nombreTR) {
        // TODO: avisar existencia de nueva TR
        
    }

    private void iniciarMonitoreoDeTR(Integer nombreTR) {
       TimerTaskTR timerTask = new TimerTaskTR(this, nombreTR);
       timersTR.put(nombreTR, timerTask);
       trCaidas.put(nombreTR, false);
    }

    private void startTimerTR(Integer nombreTR) {

        if( trCaidas.get(nombreTR) ){
            System.out.println("NC    RECU TR: " + nombreTR);
        } else {
            // Para que el timerTask anterior se corte
            timersTR.get(nombreTR).cancel();
        }
        iniciarMonitoreoDeTR(nombreTR);
        timer.schedule(timersTR.get(nombreTR), delay);
    }

    private void actualizarEstadoRedTelemetrica(HeartbeatMessege mensajeHeartbeat) {
        // TODO: Ralizar en iteraciones siguientes ;)
    }

    
}
