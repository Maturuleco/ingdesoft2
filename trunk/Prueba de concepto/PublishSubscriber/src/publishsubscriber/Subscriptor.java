/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;
import java.util.concurrent.BlockingQueue;
import model.*;

/**
 *
 * @author mar
 */
public abstract class Subscriptor extends Thread{
    private BlockingQueue<SubscriberMessage> salida;
    private BlockingQueue<SubscriptionAcceptedMessage> entrada;

    public Subscriptor() {
    }

    @Override
    public void run(){
        subscribe();
    }

    public void setEntrada(BlockingQueue<SubscriberMessage> salida) {
        this.salida = salida;
    }

    public void setSalida (BlockingQueue<SubscriptionAcceptedMessage> entrada) {
        this.entrada = entrada;
    }

    public void subscribe () {
        SubscriberMessage mensaje = crearMensajeSuscripcion();
        while (!LlegueRespuesta(mensaje)){
            salida.add(mensaje);
        }
        
    }

    protected abstract SubscriberMessage crearMensajeSuscripcion();

    private boolean LlegueRespuesta(SubscriberMessage mensaje) {
        SubscriptionAcceptedMessage respuesta = entrada.poll();
        return mensaje.equals(respuesta.getMensajeAceptado());
    }
}
