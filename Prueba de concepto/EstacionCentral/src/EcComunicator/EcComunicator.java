/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class EcComunicator {
    ControladorPuertos puertosEcs;
    
    /* Idea: El EcComunicator va a tener asignados 8 puertos, 4 de envio y 4 de recepción,
     * Los 4 de envío se implementan con Clientes, y los 4 de Recepción con Servers
     * De cada tipo hay pos tipos, uno para datos y otro para información. La idea es que el 
     * EcComunicator va a crear un thread por cada Servidor (porque no hay otra) y va a 
     * manejar los clientes en su propio hilo de ejecución. Hay 4 clases creadas ya para los
     * servers y los clientes, sólo habría que hacer news para crea las 8 instancias.
     * A los servers se le asignan colas para mandar lo que reciban, y en el caso de que 
     * reciban informacion una cola para esperar la respuesta a mandar.
     * Los clientes sólo tienen cola los de información para mandar las respuesatas de los
     * mensajes. Todas las colas las tendría que setear el EcComunicator después de crearlos.
     * Además el EcComunicator tiene un controlador de puertos que debe configurra al inicio
     * con toda la info de los puertos (tanto propios como ajenos, está hecho con xStream)
     * Va a necesitar esta info para crear los clientes y servers.
     * Luego, el run de EcComunicator, lo que tiene que hacer es crear un thread por server
     * y quedarse polleando las colas esperando ante el pedido de alguna subscripción para pasarsela al
     * cliente que corresponda.
     * 
     * Ahora que me doy cuenta, esto es para conectar sólo dos Ecs, habría que tener un juego
     * de clientes por Ec ajena...
     * 
     * Ok, a esto le falta... :P
    */
    public boolean configurar(String pathPortsEcs) {
        File configFile = new File(pathPortsEcs);
        boolean res = false;
        if (!configFile.isFile() || !configFile.canRead()) {
            System.out.println("Archivo de Puertos Inaccesible");
        }
        else {
            FileInputStream fis = null;
            try {
                configFile.setReadOnly();
                XStream xstream = new XStream();
                fis = new FileInputStream(configFile);

                puertosEcs = (ControladorPuertos) xstream.fromXML(fis);
                res = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EcComunicator.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    configFile.setWritable(true);
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(EcComunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return res;
    }
    
}