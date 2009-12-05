package NetworkComunication;



/**
 *
 * @author Ce y Mat
 */
public class ClassTestServer extends NetworkServer<MensajeSerializable,MensajeSerializable> implements Runnable {

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
            esperarYAtenderCliente();
    }

    @Override
    protected MensajeSerializable generarRespuesta(MensajeSerializable mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
