/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package publishsubscriber;

import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mar
 */
public abstract class Subscriptor {

    private BlockingQueue<? super SubscriberMessage> salida;
    private BlockingQueue<? extends SubscriptionAcceptedMessage> entrada;
    private BlockingQueue<SubscriberMessage> mensajesAceptados = new LinkedBlockingQueue<SubscriberMessage>();

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
                //System.out.println("[S]ENVIE PEDIDO SUSCRIPCION " + mensaje.toString());
            } catch (InterruptedException ex) {
                Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean LlegueRespuesta(SubscriberMessage mensaje) {
        SubscriptionAcceptedMessage respuesta = entrada.poll();
        boolean res = false;
        if (respuesta != null) {
            try {
                boolean esAceptado = mensaje.equals(respuesta.getMensajeAceptado());
                if (esAceptado) {
                    mensajesAceptados.put(respuesta.getMensajeAceptado());
                    System.out.println("[S] Llega respuesta de subscripcion al subscriptor: "+mensaje.toString() + " leng " + mensajesAceptados.size() );
                }
                res = esAceptado;
            } catch (InterruptedException ex) {
                Logger.getLogger(Subscriptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return res;
        
    }
    public boolean isSubscripto( SubscriberMessage mensaje){
//        System.out.println("mensajes aceptados: " );
//        for (SubscriberMessage object : mensajesAceptados) {
//            System.out.println(", " + object.toString() );
//        }
//        System.out.println("mensajes contenido: " + mensaje.toString() +": " + mensajesAceptados.contains(mensaje));

        return mensajesAceptados.contains(mensaje);
    }

}
