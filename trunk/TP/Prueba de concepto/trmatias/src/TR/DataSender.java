/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import red_gsm.MensajeGSM;
import model.Mensaje;
import red_gsm.MensajeModemGSM;

/**
 *
 * @author Administrador
 */

public class DataSender extends Thread {
    private static final int estacionCentral = 0;
    private static final int tamañoBuffer = 10;
    private int estadoBuffer = 0;
    private Sender[] enviando;
    private Mensaje[] mensajesPendientes;
    private BlockingQueue<Mensaje> salida;
    private BlockingQueue<Mensaje> entrada;
    private BlockingQueue<MensajeModemGSM> modemSalida;
    private BlockingQueue<MensajeModemGSM> modemEntrada;
    
    public DataSender() {
        enviando = new Sender[tamañoBuffer];
    }

    public void setEntrada(BlockingQueue<Mensaje> entrada) {
        this.entrada = entrada;
    }

    public void setModemEntrada(BlockingQueue<MensajeModemGSM> modemEntrada) {
        this.modemEntrada = modemEntrada;
    }

    public void setModemSalida(BlockingQueue<MensajeModemGSM> modemSalida) {
        this.modemSalida = modemSalida;
    }

    public void setSalida(BlockingQueue<Mensaje> salida) {
        this.salida = salida;
    }
    
    @Override
    public void run() {
        
    }

    private void send(Mensaje m, int id)
    {
        String mensaje = m.toString();
        // Le pongo un Id al Mensaje en su cuerpo
        mensaje = id+"#"+mensaje;
        Sender s = new Sender(mensaje, modemSalida);
        enviando[id] = s;
        mensajesPendientes[id] = m;
        estadoBuffer++;
        s.start();
    }

    private Boolean recive(MensajeGSM msj) {
        if (msj.getOrigen() != estacionCentral)
            return false;
        else
        {
            String contenido = new String(msj.getMensaje());
            //Saco el idMsj
            String[] cuerpo = contenido.split("#");
            if ( cuerpo[0].equals("CONFIG") )
                manejarMensajeConfiguracion(msj);
            else if ( cuerpo[0].equals("ACK") ) {
                try {
                    int id = Integer.valueOf(cuerpo[1]);
                    enviando[id].requestStop();
                    enviando[id] = null;
                    salida.put(mensajesPendientes[id]);
                    estadoBuffer--;
                } catch (InterruptedException ex) {
                    Logger.getLogger(DataSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                return false;
        }
        return true;
    }

    private void manejarMensajeConfiguracion(MensajeGSM msj) {}
    
}
