/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import model.InformationMessage;
import model.SuscriptorMessage;


/**
 *
 * @author mar
 */
public abstract class PublishSubscriber extends Thread{

    private static final long sleepTime = 100;
    private BlockingQueue<SubscriberMessage> entradaSuscripciones;
    private BlockingQueue<SuscriptorMessage> salida;
    private BlockingQueue<InformationMessage> entradaInfo;
    private List<Suscripcion> suscripciones;

    public PublishSubscriber() {    
    }

    @Override
    public void run(){
        while (true) {
            if (! sensarEntradaDatos() ) {
                try {
                    enviarInfo();
                    // Duermo un segundo
                    sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    public void setEntrada(BlockingQueue<SubscriberMessage> entrada) {
        this.entradaSuscripciones = entrada;
    }

    public void setSalida (BlockingQueue<SuscriptorMessage> salida) {
        this.salida = salida;
    }

    public void setEntradaInfo (BlockingQueue<InformationMessage> entradaInfo) {
        this.entradaInfo = entradaInfo;
    }

    private boolean sensarEntradaDatos() {
        SubscriberMessage mensaje = entradaSuscripciones.poll();
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
    
    private void enviarRespuesta(SuscriptorMessage respuesta){
        salida.add(respuesta);
        System.out.println("El Publish Subscriber acepta una suscripcion");
    }

    private void enviarInfo() {
        InformationMessage mensaje = entradaInfo.poll();
        if (mensaje != null) {
            System.out.println("El Publish Subscriber recibe informacion para enviar");
            for (Suscripcion s : suscripciones){
                if (s.seCorresponde(mensaje)){
                    Integer receptor = s.getIdSuscriptor();
                    InformationMessage respuesta = new InformationMessage(receptor,mensaje);
                    enviarRespuesta(respuesta);
                }
            }
        }
    }


}
