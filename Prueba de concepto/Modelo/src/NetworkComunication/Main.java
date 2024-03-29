package NetworkComunication;

import Datos.DataSource;

/**
 *
 * @authors Ce y Mat
 */

public class Main {
    
    public static void main(String[] args){
        int port = 2002;
        MensajeSerializable msj = new MensajeSerializable(26, DataSource.terminal_remota);

        ClassTestClient cliente = new ClassTestClient(port);
        System.out.println("No puedo ¿no?: "+cliente.enviarMensaje(msj, true));


        ClassTestServer server = new ClassTestServer(port);
        new Thread(server).start();
        
        System.out.println("Cuando conecto, poder: "+cliente.enviarMensaje(msj, true));
        
        System.out.println("Mensaje que mandé: "+msj.toString());

    }
}
