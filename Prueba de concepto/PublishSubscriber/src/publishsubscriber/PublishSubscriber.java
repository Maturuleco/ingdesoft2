/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import model.*;

/**
 *
 * @author mar
 */
public abstract class PublishSubscriber extends Thread{

    private static final long sleepTime = 100;
    private BlockingQueue<SubscriberMessage> entrada;
    private BlockingQueue<SubscriptionAcceptedMessage> salida;
    private List<Suscripcion> suscripciones;

    public PublishSubscriber() {    
    }

    @Override
    public void run(){
        while (true) {
            if (! sensarEntradaDatos() ) {
                try {
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    public void setEntrada(BlockingQueue<SubscriberMessage> entrada) {
        this.entrada = entrada;
    }

    public void setSalida (BlockingQueue<SubscriptionAcceptedMessage> salida) {
        this.salida = salida;
    }

    private boolean sensarEntradaDatos() {
        SubscriberMessage mensaje = entrada.poll();
        if (mensaje != null) {
            System.out.println("El Publish Subscriber recibe una suscripcion");
            Suscripcion suscripcion = crearSuscripcion(mensaje);
            suscripciones.add(suscripcion);
            SubscriptionAcceptedMessage respuesta = new SubscriptionAcceptedMessage(mensaje);
            enviarRespuesta(respuesta);
            return true;
        }
        return false;
    }

    protected abstract Suscripcion crearSuscripcion(SubscriberMessage mensaje);
    
    private void enviarRespuesta(SubscriptionAcceptedMessage respuesta){
        salida.add(respuesta);
        System.out.println("El Publish Subscriber acepta una suscripcion");
    }


}
