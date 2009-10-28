/*
 * 
 * Esta Clase se ocupa de la transmición de Mensajes,
 * cuando le llega un Mensaje por su cola de Entrada
 * se ocupa de transmitirlo y cuando se asegura de que llegó
 * manda el mismo mensaje por la cola de salida.
 * 
 */

package DataSender;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import red_gsm.MensajeToModemGSM;
import model.Mensaje;
import model.ValidatingTools;
import red_gsm.MensajeGSM;


/**
 *
 * @author Tas
 * 
 */

public class DataSender extends Thread {
    private BlockingQueue<MensajeToModemGSM> modemSalida;
    private BlockingQueue<MensajeGSM> modemEntrada;
    private BlockingQueue<Mensaje> salida;
    private BlockingQueue<Mensaje> entrada;
    private static final long sleepTime = 10000;
    
    private Mensaje mensajeActual;
    private int msjEnProceso = 0;
    private long timeStampMsjActual;
    private Sender[] enviando;
        
    public DataSender () {
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setModemEntrada(BlockingQueue<MensajeGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    public void setModemSalida(BlockingQueue<MensajeToModemGSM> modemSalida) {
        this.modemSalida = modemSalida;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salida = salida;
    }
    
    @Override
    public void run() {
        while (true) {
            if (msjEnProceso == 0) {
                if (! sensarEntradaDatos() ) {
                    try {
                        // Duermo un segundo
                        sleep(sleepTime);
                    } catch (InterruptedException ex) {}
                }
            }
            while (msjEnProceso != 0){
                sensarEntradaModem();
                try {
                    // Duermo 1/2 segundo
                    sleep(500);
                    } catch (InterruptedException ex) {}
            }
            // Esto está para ir leyendo los posibles mensajes con delay
            // que me lleguen de comunicaciones pasadas
            sensarEntradaModem();
        }
    }
    
    private boolean sensarEntradaDatos() {
        Mensaje cabeza = entrada.poll();
        if (cabeza != null) {
            send(cabeza);
            return true;
        }
        return false;
    }
    
    private void sensarEntradaModem() {
        MensajeGSM respuesta = modemEntrada.poll();
        if (respuesta != null)
            recive(respuesta);
    }

    private void formatPartes(String[] partes) {
        for (int i = 0; i < partes.length-1; i++) {
            String msj = partes[i];
            // Le pongo un Id al Mensaje en su cuerpo
            // Tb le pongo una M (more) para decir que
            // no es la última fracción
            msj = "M#"+i+"#"+msj;
            partes[i] = msj;
        }
        // Hago uno para el último
        int i = partes.length - 1;
        String msj = partes[i];
        // L de Last
        msj = "L#"+i+"#"+msj;
        partes[i] = msj;
    }
    
    private String[] fraccionar(String msj) {
        // TODO: los msj SMS tienen 160 caracteres!
        String[] partes = new String[1];
        partes[0] = msj;
        return partes;
    }
    
    private void firmar(String[] mensajes){
        for (int i = 0; i < mensajes.length; i++){
            String hash = ValidatingTools.getHash(mensajes[i]);
            mensajes[i] += '#'+hash;
        }
    }
    
    private void send(Mensaje m)
    {
        mensajeActual = m;
        timeStampMsjActual = m.getTimeStamp();
        String mensaje = m.toString();
        String[] partes = fraccionar(mensaje);
                
        formatPartes(partes);
        firmar(partes);
        
        enviando = new Sender[partes.length];        
        for (int i = 0; i < partes.length; i++) {
            Sender s = new Sender(partes[i], modemSalida);
            enviando[i] = s;
            msjEnProceso++;
            s.start();
        }
    }
    
    private Boolean recive(MensajeGSM msj) {
        String contenido = new String(msj.getMensaje());
        
        String[] cuerpo = contenido.split("#");
        // Esto ya lo validé antes, pero está buno re-hacerlo.. capaz
        if ( cuerpo[0].equals("ACK") ) {
            int id = Integer.valueOf(cuerpo[1]);
            if ( checkACK(id, Long.valueOf(cuerpo[2]))) {
                enviando[id].requestStop();
                enviando[id] = null;
                msjEnProceso--;
                // Cuando lo termino de transmitir se lo devuelvo
                if (msjEnProceso == 0)
                    try {
                        salida.put(mensajeActual);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                return true;
            }
        }
        return false;
    }
    
    private Boolean checkACK(int id, long timeStamp) {
        // Checkeo de que el ACK no sea de un mensaje viejo
        return (timeStampMsjActual == timeStamp);
    }
    
}
