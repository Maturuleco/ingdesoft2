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
public class ServerInformacion<T> extends NetworkServer<InformationMessage<T>, InformationMessage<T>> implements Runnable {

    private BlockingQueue<T> salidaDeInformacion;
    
    public ServerInformacion(int port) {
        super(port);
    }

    public void setSalidaDeInformacion(BlockingQueue<T> salidaDeInformacion) {
        this.salidaDeInformacion = salidaDeInformacion;
    }

    
    public void run() {
        while (true) {
            super.esperarYAtenderCliente();
        }
    }
    
    @Override
    protected void procesarMensaje(InformationMessage<T> mensaje) {
        try {
            System.out.println("[EcCom]Se recibe informacion externa: " + mensaje.getMensaje().toString() );
            salidaDeInformacion.put(mensaje.getMensaje());
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerInformacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected InformationMessage<T> generarRespuesta(InformationMessage<T> mensaje) {
        return null;
    }

}
