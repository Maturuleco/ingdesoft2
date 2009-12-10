/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EcComunicator;

import NetworkComunication.NetworkDestination;
import SubscripcionesEc.MensajePedidoSubscripcionDatos;
import SubscripcionesEc.MensajePedidoSubscripcionResultados;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.InformationMessage;

/**
 *
 * @author tas
 */
public class EcComunicator implements Runnable {

    private static final long sleeptime = 1000;
    private ControladorPuertos puertosEcs;
    private Boolean serversReady = false;

    private ClienteInformacion clienteEnvioDatos;
    private ClienteInformacion clienteEnvioResult;
    private ClienteSubscripciones clienteSubsDatos;
    private ClienteSubscripciones clienteSubsResult;

    private ServerInformacion<InformationMessage> serverDatos;
    private ServerInformacion<InformationMessage> serverResultados;
    private ServerSubscripcones<MensajePedidoSubscripcionDatos> serverSubsDatos;
    private ServerSubscripcones<MensajePedidoSubscripcionResultados> serverSubsResult;

    private BlockingQueue<SubscriberMessage> entradaEnvioSubscripcionesDatos;
//    private BlockingQueue<SubscriptionAcceptedMessage> salidaRespSubscripcionesDatos;

    private BlockingQueue<SubscriberMessage> entradaEnvioSubscripcionesResult;
//    private BlockingQueue<SubscriptionAcceptedMessage> salidaRespSubscripcionesResult;

    private BlockingQueue<InformationMessage> entradaDatos;
    private BlockingQueue<InformationMessage> entradaResult;

//    private BlockingQueue<InformationMessage> salidaDatosExternos;
//    private BlockingQueue<InformationMessage> salidaResultExternos;

//    private BlockingQueue<SubscriberMessage> salidaSubscripExternasDatos;
//    private BlockingQueue<SubscriptionAcceptedMessage> entradaRespSubscripExternasDatos;

//    private BlockingQueue<SubscriberMessage> salidaSubscripExternasResult;
//    private BlockingQueue<SubscriptionAcceptedMessage> entradaRespSubscripExternasResult;

    /* Idea: El EcComunicator va a tener asignados 8 puertos, 4 de envio y 4 de recepción,
     * Los 4 de envío se implementan con Clientes, y los 4 de Recepción con Servers
     * De cada tipo hay pos tipos, uno para datos y otro para información. La idea es que el 
     * EcComunicator va a crear un thread por cada Servidor (porque no hay otra) y va a 
     * manejar los clientes en su propio hilo de ejecución. Hay 4 clases creadas ya para los
     * servers y los clientes, sólo habría que hacer news para crea las 8 instancias.
     * 
     * A los servers se le asigna un cola para mandar lo que reciban, y en el caso de que 
     * reciban informacion una cola para esperar la respuesta a mandar.
     * 
     * En cuanto a los clientes, sólo tienen cola los de subscripciones, la usan para mandar las
     * respuesatas de los mensajes.
     * Todas las colas las tendría que setear el EcComunicator después de crearlos.
     * Además el EcComunicator tiene un controlador de puertos que debe configurra al inicio
     * con toda la info de los puertos (tanto propios como ajenos, está hecho con xStream)
     * Va a necesitar esta info para mandar los mensajes y crear los servers.
     * 
     * Luego, el run de EcComunicator, lo que tiene que hacer es crear un thread por server
     * y quedarse polleando las colas esperando ante el pedido de alguna subscripción para pasarsela al
     * cliente que corresponda.
     */
    
    public EcComunicator(String pathArchivoPuertosEcs) {
        this.clienteEnvioDatos = new ClienteInformacion();
        this.clienteEnvioResult = new ClienteInformacion();
        this.clienteSubsDatos = new ClienteSubscripciones();
        this.clienteSubsResult = new ClienteSubscripciones();
        initialize(pathArchivoPuertosEcs);
    }

    //Datos Internos :
    public void setEntradaDatos(BlockingQueue<InformationMessage> entradaInformacion) {
        this.entradaDatos = entradaInformacion;
    }

    public void setEntradaResult(BlockingQueue<InformationMessage> entradaResult) {
        this.entradaResult = entradaResult;
    }
    // Subscripciones Internas :
    public void setEntradaEnvioSubscripcionesDatos(BlockingQueue<SubscriberMessage> entradaEnvioSubscripcionesDatos) {
        this.entradaEnvioSubscripcionesDatos = entradaEnvioSubscripcionesDatos;
    }

    public void setEntradaEnvioSubscripcionesResult(BlockingQueue<SubscriberMessage> entradaEnvioSubscripcionesResult) {
        this.entradaEnvioSubscripcionesResult = entradaEnvioSubscripcionesResult;
    }

    public void setSalidaRespSubscripcionesDatos(BlockingQueue<SubscriptionAcceptedMessage> salidaRespSubsDatos) {
        clienteSubsDatos.setSalida(salidaRespSubsDatos);
    }

    public void setSalidaRespSubscripcionesResult(BlockingQueue<SubscriptionAcceptedMessage> salidaRespSubsResult) {
        clienteSubsResult.setSalida(salidaRespSubsResult);
    }
    // Datos Externos :
    public void setSalidaDatosExternos(BlockingQueue<InformationMessage> salidaDatos) {
        serverDatos.setSalidaDeInformacion(salidaDatos);
    }

    public void setSalidaResultExternos(BlockingQueue<InformationMessage> salidaResult) {
        serverResultados.setSalidaDeInformacion(salidaResult);
    }
    // Subscripciones Externas :
    public void setSalidaSubscripExternasDatos(BlockingQueue<SubscriberMessage> salidaSubscripExternasDatos) {
        serverSubsDatos.setSalidaDeSubscripciones(salidaSubscripExternasDatos);
    }

    public void setEntradaRespSubscripExternasDatos(BlockingQueue<SubscriptionAcceptedMessage> entradaRespSubscripExternasDatos) {
        serverSubsDatos.setEntradaDeRespuestasDeSubscripciones(entradaRespSubscripExternasDatos);
    }

    public void setSalidaSubscripExternasResult(BlockingQueue<SubscriberMessage> salidaSubscripExternasResult) {
        serverSubsResult.setSalidaDeSubscripciones(salidaSubscripExternasResult);
    }

    public void setEntradaRespSubscripExternasResult(BlockingQueue<SubscriptionAcceptedMessage> entradaRespSubscripExternasResult) {
        serverSubsResult.setEntradaDeRespuestasDeSubscripciones(entradaRespSubscripExternasResult);
    }

    public void initialize(String pathPortsEcs) {
        if (configurar(pathPortsEcs)) {
            Integer idEc = estacioncentral.Main.idEc;
            this.serverDatos = new ServerInformacion<InformationMessage>(puertosEcs.getPort(idEc, TypeServerDestinyPort.envio_datos));
            this.serverResultados = new ServerInformacion<InformationMessage>(puertosEcs.getPort(idEc, TypeServerDestinyPort.envio_resultado));
            this.serverSubsDatos = new ServerSubscripcones<MensajePedidoSubscripcionDatos>(puertosEcs.getPort(idEc, TypeServerDestinyPort.envio_subscripcion_datos));
            this.serverSubsResult = new ServerSubscripcones<MensajePedidoSubscripcionResultados>(puertosEcs.getPort(idEc, TypeServerDestinyPort.envio_subscripcion_resultados));

            serversReady = true;
        }
    }

    private boolean configurar(String pathPortsEcs) {
        File configFile = new File(pathPortsEcs);
        boolean res = false;
        if (!configFile.isFile() || !configFile.canRead()) {
            System.out.println("\n\t####----ALERTA----####\tArchivo de Puertos Inaccesible.\n");
        } else {
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

    public void run() {
        new Thread(serverDatos).start();
        new Thread(serverResultados).start();
        new Thread(serverSubsDatos).start();
        new Thread(serverSubsResult).start();
        System.out.println("EcCONTROLLER\tSe Inician los Servers para recibie informacion y subscripciones externas");
        while (true) {
            if (entradaDatos.size() > 0) {
                InformationMessage dato = entradaDatos.poll();
                procesarDato(dato);
            } else if (entradaResult.size() > 0) {
                InformationMessage result = entradaResult.poll();
                procesarResult(result);
            } else if (entradaEnvioSubscripcionesDatos.size() > 0) {
                SubscriberMessage subscripcion = entradaEnvioSubscripcionesDatos.poll();
                procesarSubscripcionDatos(subscripcion);
            } else if (entradaEnvioSubscripcionesResult.size() > 0) {
                SubscriberMessage subscripcion = entradaEnvioSubscripcionesResult.poll();
                procesarSubscripcionResultado(subscripcion);
            } else {
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(EcComunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void procesarDato(InformationMessage dato) {
        NetworkDestination destino = puertosEcs.getNetworkDestination(dato.getReceptor(), TypeServerDestinyPort.envio_datos);
        clienteEnvioDatos.send(dato, destino);
    }

    private void procesarResult(InformationMessage result) {
        NetworkDestination destino = puertosEcs.getNetworkDestination(result.getReceptor(), TypeServerDestinyPort.envio_resultado);
        clienteEnvioResult.send(result, destino);
    }

    private void procesarSubscripcionDatos(SubscriberMessage subscripcion) {
        NetworkDestination destino = puertosEcs.getNetworkDestination(subscripcion.getEcProovedora(), TypeServerDestinyPort.envio_subscripcion_datos);
        clienteSubsDatos.send(subscripcion, destino);
    }

    private void procesarSubscripcionResultado(SubscriberMessage subscripcion) {
        NetworkDestination destino = puertosEcs.getNetworkDestination(subscripcion.getEcProovedora(), TypeServerDestinyPort.envio_subscripcion_resultados);
    }
}
