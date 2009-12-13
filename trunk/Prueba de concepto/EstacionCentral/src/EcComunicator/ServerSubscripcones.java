/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkServer;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class ServerSubscripcones<T extends SubscriberMessage> extends NetworkServer<T, SubscriptionAcceptedMessage> implements Runnable {
    
    private BlockingQueue<SubscriberMessage> salidaDeSubscripciones;
    private BlockingQueue<SubscriptionAcceptedMessage> entradaDeRespuestasDeSubscripciones;
    
    public ServerSubscripcones(int port) {
        super(port);
    }

    public void setEntradaDeRespuestasDeSubscripciones(BlockingQueue<SubscriptionAcceptedMessage> entradaDeRespuestasDeSubscripciones) {
        this.entradaDeRespuestasDeSubscripciones = entradaDeRespuestasDeSubscripciones;
    }

    public void setSalidaDeSubscripciones(BlockingQueue<SubscriberMessage> salidaDeSubscripciones) {
        this.salidaDeSubscripciones = salidaDeSubscripciones;
    }

    
    public void run() {
        while(true) {
            super.esperarYAtenderCliente();
        }
    }
    @Override
    protected void procesarMensaje(T mensaje) {
        System.out.println("[EcCom] Se recibe pedido de subscripcion");
        try {
            salidaDeSubscripciones.put(mensaje);
            System.out.println("[EcCom] Se manda pedido de subscripcions al DataSender");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerSubscripcones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected SubscriptionAcceptedMessage generarRespuesta(T mensaje) {
        SubscriptionAcceptedMessage respuesta = null;
        try {
            respuesta = entradaDeRespuestasDeSubscripciones.take();
            System.out.println("[EcCom] Se recibe la respuesta desde el DataSender");
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerSubscripcones.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

}
