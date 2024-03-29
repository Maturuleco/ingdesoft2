package NetworkComunication;



/**
 *
 * @author Ce y Mat
 */

public class ClassTestClient extends NetworkClient<MensajeSerializable, MensajeSerializable> {

    public ClassTestClient(int port) {
        super.setDestino(new NetworkDestination("localhost", port));
    }

    @Override
    protected void procesarMensajeDeRespuesta(MensajeSerializable mensaje) {
        System.out.println("procesando mensajes de respuesta: " + mensaje.toString());
    }


}
