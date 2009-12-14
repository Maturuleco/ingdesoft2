package NetworkComunication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.InformationMessage;

/**
 *
 * @author Ce y Mat
 */

public abstract class NetworkServer<TipoMensajeEntrada extends Serializable, TipoMensajeSalida extends Serializable> {
//public abstract class NetworkServer<TipoMensajeEntrada extends MensajeGeneral> {

    private ServerSocket socket;
    private Socket socketCliente;

    protected abstract void procesarMensaje(TipoMensajeEntrada mensaje);
    
    //Si no se desea enviar una respuesta, devolver null
    protected abstract TipoMensajeSalida generarRespuesta(TipoMensajeEntrada mensaje);

    public NetworkServer(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public void esperarYAtenderCliente(){
        try {
            socketCliente = socket.accept();

            InputStream entrada = socketCliente.getInputStream();
            ObjectInputStream entradaObjetos = new ObjectInputStream (entrada);

            Object obj = entradaObjetos.readObject();
            TipoMensajeEntrada mensajeRecibido = (TipoMensajeEntrada) obj;
            procesarMensaje(mensajeRecibido);

            TipoMensajeSalida respuesta = generarRespuesta(mensajeRecibido);

            if (respuesta != null)
                enviarRespuesta(respuesta);

        } catch (ClassCastException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void enviarRespuesta(TipoMensajeSalida respuesta){
        OutputStream salida = null;
        try {
            salida = socketCliente.getOutputStream();
            ObjectOutputStream salidaObjetos = new ObjectOutputStream(salida);

            salidaObjetos.writeObject(respuesta);

        } catch (IOException ex) {
            Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                salida.close();
            } catch (IOException ex) {
                Logger.getLogger(NetworkServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
