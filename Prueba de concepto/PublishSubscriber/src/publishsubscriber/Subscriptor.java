/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author mar
 */
public abstract class Subscriptor {//extends Thread{
    private BlockingQueue<SubscriberMessage> salida;
    private BlockingQueue<SuscriptorMessage> entrada;

    public Subscriptor() {
    }

/*
    @Override
    public void run(){
        while(true){
            subscribe();
        }
    }
*/
    public void setEntrada(BlockingQueue<SuscriptorMessage> entrada) {
        this.entrada = entrada;
    }

    public void setSalida(BlockingQueue<SubscriberMessage> salida) {
        this.salida = salida;
    }

    public void subscribe (SubscriberMessage mensaje) {
        while (!LlegueRespuesta(mensaje)){
            try {
                Thread.sleep(1000);
                salida.add(mensaje);
            } catch (InterruptedException ex) {
                Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    //protected abstract SubscriberMessage crearMensajeSuscripcion();

    private boolean LlegueRespuesta(SubscriberMessage mensaje) {
        SubscriptionAcceptedMessage respuesta = entrada.poll();
        return mensaje.equals(respuesta.getMensajeAceptado());
    }
}
