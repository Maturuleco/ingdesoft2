package NetworkComunication;



/**
 *
 * @author Ce y Mat
 */

public class ClassTestClient extends NetworkClient<MensajeSerializable, MensajeSerializable> {

    public ClassTestClient(int port) {
        super(port);
    }

    @Override
    protected void procesarMensajeDeRespuesta(MensajeSerializable mensaje) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
