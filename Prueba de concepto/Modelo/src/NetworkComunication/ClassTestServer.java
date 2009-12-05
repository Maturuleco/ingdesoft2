package NetworkComunication;

import model.Mensaje;

/**
 *
 * @author Ce y Mat
 */
public class ClassTestServer extends NetworkServer<MensajeSerializable> implements Runnable {

    public MensajeSerializable m =null;

    public ClassTestServer(int port) {
        super(port);
    }


    @Override
    protected void procesarMensaje(MensajeSerializable mensaje) {
        System.out.println("En el client: "+mensaje.toString());
        m = mensaje;
    }

    public void run() {
        while (true)
            atenderCliente();
    }


}
