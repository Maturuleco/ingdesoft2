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

/**
 *
 * @author mar
 */
public abstract class Subscriptor {

    private BlockingQueue<? super SubscriberMessage> salida;
    private BlockingQueue<? extends SubscriptionAcceptedMessage> entrada;

    public Subscriptor() {
    }

    public void setEntradaRespuestas(BlockingQueue<? extends SubscriptionAcceptedMessage> entrada) {
        this.entrada = entrada;
    }

    public void setSalidaSubscripciones(BlockingQueue<? super SubscriberMessage> salida) {
        this.salida = salida;
    }

    public void subscribe(SubscriberMessage mensaje) {
        while (!LlegueRespuesta(mensaje)) {
            try {
                Thread.sleep(1000);
                salida.put(mensaje);
//                System.out.println("[S]ENVIE PEDIDO SUSCRIPCION");
            } catch (InterruptedException ex) {
                Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean LlegueRespuesta(SubscriberMessage mensaje) {
        SubscriptionAcceptedMessage respuesta = entrada.poll();
        if (respuesta != null) {
            System.out.println("[S] Llega respuesta de subscripcion al subscriptor: "+(mensaje.equals(respuesta.getMensajeAceptado())));
            return mensaje.equals(respuesta.getMensajeAceptado());
        } else{
            return false;
        }
    }
}
