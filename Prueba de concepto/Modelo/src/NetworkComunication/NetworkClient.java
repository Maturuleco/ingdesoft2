package NetworkComunication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MensajeGeneral;

/**
 *
 * @author Ce y Mat
 */
public abstract class NetworkClient<TipoMensajeDeEnvio extends MensajeGeneral> {

    private String host;
    private Socket socket = null;
    private int port;

    public NetworkClient(int port) {
        this("localhost", port);
    }

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private boolean conectar() {
        try {
            this.socket = new Socket(host, port);
            return true;
        
        } catch (ConnectException ex) {
            System.out.println("No se pudo conectar al puerto "+port+" en el host "+host+"\n");
        } catch (UnknownHostException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }


    protected boolean enviarMensaje(TipoMensajeDeEnvio mensajeDeEnvio) {
        Boolean mensajeEnviado = false;
        if (conectar()) {
            OutputStream salida = null;
            try {
                salida = socket.getOutputStream();
                ObjectOutputStream salidaObjetos = new ObjectOutputStream(salida);

                salidaObjetos.writeObject(mensajeDeEnvio);

                mensajeEnviado = true;
            } catch (IOException ex) {
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    salida.close();
                } catch (IOException ex) {
                    Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return mensajeEnviado;
    }

}
