/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import NetworkComunication.NetworkServer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.InformationMessage;

/**
 *
 * @author tas
 */
public class ServerInformacion<T extends InformationMessage> extends NetworkServer<T, InformationMessage> implements Runnable {

    private BlockingQueue<InformationMessage> salidaDeInformacion;
    
    public ServerInformacion(int port) {
        super(port);
    }

    public void setSalidaDeInformacion(BlockingQueue<InformationMessage> salidaDeInformacion) {
        this.salidaDeInformacion = salidaDeInformacion;
    }

    
    public void run() {
        super.esperarYAtenderCliente();
    }
    
    @Override
    protected void procesarMensaje(T mensaje) {
        try {
            salidaDeInformacion.put(mensaje);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected InformationMessage generarRespuesta(T mensaje) {
        return null;
    }

}
