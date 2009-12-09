/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkClient;
import NetworkComunication.NetworkDestination;
import model.InformationMessage;

/**
 *
 * @author tas
 */
public class ClienteInformacion extends NetworkClient<InformationMessage, InformationMessage> {

    public ClienteInformacion(NetworkDestination nd) {
        super(nd);
    }

    public void sendData(InformationMessage dato){
        super.enviarMensaje(dato, false);
    }
    
    @Override
    protected void procesarMensajeDeRespuesta(InformationMessage mensaje) {
        throw new UnsupportedOperationException("No se deben recibir respuestas de envios de Datos.");
    }
    
}
