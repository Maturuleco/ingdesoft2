/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package publishsubscriber;

import SubscripcionesEc.MensajePedidoSubscripcionDatos;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import model.InformationMessage;
import model.SuscriptorMessage;


/**
 *
 * @author mar
 */
public abstract class PublishSubscriber<T> implements Runnable {

    private static final long sleepTime = 100;
    private BlockingQueue<SubscriberMessage> entradaSuscripciones;
    private BlockingQueue<SubscriptionAcceptedMessage> salidaAceptacionSubs;
    private BlockingQueue<T> entradaInfo;
    private BlockingQueue<InformationMessage<T>> salidaInfo;
    private Set<Suscripcion> suscripciones = new HashSet<Suscripcion>();

    public PublishSubscriber() {    
    }

    @Override
    public void run(){
        while (true) {
            if (! sensarEntradaSubscripciones() ) {
                try {
                    enviarInfo();
                    // Duermo un segundo
                    Thread.sleep(sleepTime);
                } catch (InterruptedException ex) {}
            }
        }
    }

    public void setEntrada(BlockingQueue<SubscriberMessage> entrada) {
        this.entradaSuscripciones = entrada;
    }

    public void setSalidaAceptacionSubs(BlockingQueue<SubscriptionAcceptedMessage> salidaAceptacionSubs) {
        this.salidaAceptacionSubs = salidaAceptacionSubs;
    }

    public void setSalidaInfo(BlockingQueue<InformationMessage<T>> salidaInfo) {
        this.salidaInfo = salidaInfo;
    }

    public void setEntradaInfo (BlockingQueue<T> entradaInfo) {
        this.entradaInfo = entradaInfo;
    }

    private boolean sensarEntradaSubscripciones() {
        SubscriberMessage mensaje = entradaSuscripciones.poll();
        if (mensaje != null) {
            System.out.println("El Publish Subscriber recibe una suscripcion");
            Suscripcion suscripcion = crearSuscripcion(mensaje);
            suscripciones.add(suscripcion);
            SubscriptionAcceptedMessage respuesta = new SubscriptionAcceptedMessage(mensaje);
            enviarRespuesta(respuesta);
            System.out.println("El Publish Subscriber acepta una suscripcion");
            return true;
        }
        return false;
    }

    protected abstract Suscripcion crearSuscripcion(SubscriberMessage mensaje);
    
    private void enviarRespuesta(SubscriptionAcceptedMessage respuesta){
        salidaAceptacionSubs.add(respuesta);
        System.out.println("El Publish Subscriber acepta una suscripcion: ecProveedora: "+respuesta.getMensajeAceptado().getEcProovedora()+" IdSuscriptor: "+respuesta.getMensajeAceptado().getIdSuscriptor());
        System.out.println(" idTr: "+((MensajePedidoSubscripcionDatos)(respuesta.getMensajeAceptado())).getTR()+" Factor: "+((MensajePedidoSubscripcionDatos)(respuesta.getMensajeAceptado())).getFactorClimatico());

    }

    private void enviarRespuesta(InformationMessage<T> info){
        salidaInfo.add(info);
        System.out.println("El Publish Subscriber manda un dato.");
    }

    private void enviarInfo() {
        T mensaje = entradaInfo.poll();
        if (mensaje != null) {
            System.out.println("El Publish Subscriber recibe informacion para enviar");
            for (Suscripcion<T> s : suscripciones){
                if (s.seCorresponde(mensaje)){
                    Integer receptor = s.getIdSuscriptor();
                    InformationMessage<T> respuesta = new InformationMessage<T>(receptor,mensaje);
                    enviarRespuesta(respuesta);
                }
            }
        }
    }


}
