/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkClient;
import NetworkComunication.NetworkDestination;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class ClienteSubscripciones extends NetworkClient<SubscriberMessage, SubscriptionAcceptedMessage> {
    
    private BlockingQueue<SubscriptionAcceptedMessage> salida;
            
    public void setSalida(BlockingQueue<SubscriptionAcceptedMessage> salida) {
        this.salida = salida;
    }

    public void send(SubscriberMessage msj, NetworkDestination destino){
        System.out.println("[EcCom]Se manda pedido de subscripcion "+msj.toString());
        super.enviarMensaje(destino, msj, true);
    }
    
    @Override
    protected void procesarMensajeDeRespuesta(SubscriptionAcceptedMessage mensaje) {
        try {
            salida.put(mensaje);
            } catch (InterruptedException ex) {
            Logger.getLogger(ClienteSubscripciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
