package NetworkComunication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ce y Mat
 */
public abstract class NetworkClient<TipoMensajeDeEnvio extends Serializable, TipoMensajeDeRespuesta extends Serializable> {

    private String host;
    private Socket socket = null;
    private int port;

    protected abstract void procesarMensajeDeRespuesta(TipoMensajeDeRespuesta mensaje);

    public NetworkClient(int port) {
        this(new NetworkDestination("localhost", port));
    }

    public NetworkClient(NetworkDestination nd) {
        this.host = nd.getHost();
        this.port = nd.getPort();
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


    protected boolean enviarMensaje(TipoMensajeDeEnvio mensajeDeEnvio, Boolean conRespuesta) {
        Boolean mensajeEnviado = false;
        if (conectar()) {
            OutputStream salida = null;
            try {
                salida = socket.getOutputStream();
                ObjectOutputStream salidaObjetos = new ObjectOutputStream(salida);

                salidaObjetos.writeObject(mensajeDeEnvio);

                if (conRespuesta)
                    esperarRespuesta();

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

    private void esperarRespuesta() {
        InputStream entrada = null;
        try {
            entrada = socket.getInputStream();
            ObjectInputStream entradaObjetos = new ObjectInputStream(entrada);
            // Validar que el readObj espera hasta que le envian la respuesta
            Object obj = entradaObjetos.readObject();
            TipoMensajeDeRespuesta mensajeRecibido = (TipoMensajeDeRespuesta) obj;
            procesarMensajeDeRespuesta(mensajeRecibido);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                entrada.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
