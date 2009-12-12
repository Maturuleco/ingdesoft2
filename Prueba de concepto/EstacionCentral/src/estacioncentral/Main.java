package estacioncentral;

import DataSender.DatosPublishSubscriber;
import com.db4o.Db4o;
import com.db4o.ObjectServer;
import dataReceiver.DataReceiver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import messageReceiver.TRMessageReciever;
import networkController.NetworkController;
import predictor.PredictorManager;
import validator.ValidatorManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import Datos.DatoAlmacenado;
import EcComunicator.EcComunicator;
import model.Mensaje;
import EstadoDeRed.HeartbeatMessege;
import ModeloTerminal.ModeloTerminalRemota;
import SubscripcionesEc.SubscriberMessage;
import SubscripcionesEc.SubscriptionAcceptedMessage;
import SubscriptorModelos.SubscriptorModelos;
import ValidDataManager.ValidDataManager;
import evaluador.ResultadoEvaluacion;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import model.InformationMessage;
import model.SuscriptorMessage;
import modelo.Modelo;
import red_gsm.ComparadorMsjGSM;
import red_gsm.MensajeGSM;
import red_gsm.ModemGSM;

/**
 *
 * @author Santiago Avendaño
 */
public class Main {
    public static int idEc;
    public static int numeroModem;
    public static String carpetaParaModelos;
    private static String archivoDePuertosDeLaRed;
    
    private static Collection<ModeloTerminalRemota> terminales = new LinkedList<ModeloTerminalRemota>();

    private static PredictorManager predictor;
    private static ValidatorManager validator;
    private static ValidDataManager validDataManager;
    private static DataReceiver dataReceiver;
    private static TRMessageReciever messageReceiver;
    private static NetworkController networkController;
    private static ModemDispatcher modemDispatcher;
    private static ModemGSM modemGSM;
    private static EcComunicator ecComunicator;
    private static DatosPublishSubscriber dataSender;
    private static SubscriptorModelos subscriptorModelos;
    
    private static BlockingQueue<Mensaje> trReceiverToData =
            new LinkedBlockingQueue<Mensaje>();
    private static BlockingQueue<DatoAlmacenado> dataToValidator =
            new LinkedBlockingQueue<DatoAlmacenado>();
    private static BlockingQueue<DatoAlmacenado> validatorToValidDataManager =
            new LinkedBlockingQueue<DatoAlmacenado>();
    private static BlockingQueue<HeartbeatMessege> dataToNetwork =
            new LinkedBlockingQueue<HeartbeatMessege>();
    private static BlockingQueue<MensajeGSM> salidaModem =
            new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> entradaModem =
            new PriorityBlockingQueue<MensajeGSM>(3, new ComparadorMsjGSM());
    private static BlockingQueue<MensajeGSM> dispatcherReceiver =
            new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> dispatcherNetwork =
            new LinkedBlockingQueue<MensajeGSM>();
    
    private static BlockingQueue<SubscriberMessage> colaSubsDatos =
            new LinkedBlockingQueue<SubscriberMessage>();
    private static BlockingQueue<SubscriberMessage> colaSubsResul =
            new LinkedBlockingQueue<SubscriberMessage>();
    private static BlockingQueue<SubscriptionAcceptedMessage> respuestaSubs =
            new LinkedBlockingQueue<SubscriptionAcceptedMessage>();

    private static BlockingQueue<SubscriberMessage> pedidosDeSubsADatos =
            new LinkedBlockingQueue<SubscriberMessage>();
    private static BlockingQueue<SuscriptorMessage> aceptacionSubsDatos =
            new LinkedBlockingQueue<SuscriptorMessage>();
    private static BlockingQueue<InformationMessage<DatoAlmacenado>> datosSalientes =
            new LinkedBlockingQueue<InformationMessage<DatoAlmacenado>>();
    private static BlockingQueue<InformationMessage<ResultadoEvaluacion>> resultadosSalientes =
            new LinkedBlockingQueue<InformationMessage<ResultadoEvaluacion>>();
    private static BlockingQueue<Modelo> modelosSubscripcion =
            new LinkedBlockingQueue<Modelo>();
    private static BlockingQueue<Collection<ResultadoEvaluacion>> salidasResultados =
            new LinkedBlockingQueue<Collection<ResultadoEvaluacion>>();
    private static BlockingQueue<SubscriberMessage> salidaSubscripExternasDatos =
            new LinkedBlockingQueue<SubscriberMessage>();

    private static ObjectServer validDataServer;
    private static ObjectServer outlierDataServer;
    private static ObjectServer resultadosServer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Es necesario el archivo de configuracion\n");
        } else {
          //  System.out.println(args[0]);
            String configPath = args[0];
            File configFile = new File(configPath);
            if (!configFile.isFile()) {
                System.out.println("Es necesario el archivo de configuracion\n");
            } else {
                try {

                    configurar(configFile);
                    inicializarComponentes();
                    conectarComponentes();
                    prenderComponentes();
                
                } catch (ParseException ex) {
                    System.out.println("Archivo de configuracion invalido\n");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    System.out.println("Path invalido\n");
                }
            }
        }
    }
    
    private static String getDatoFromLine(String linea) throws ParseException {
        if (linea == null) {
            throw new ParseException("Configuration file", 0);
        }
        String[] partes = linea.split(":");
        if (partes.length != 2) {
            throw new ParseException("En archivo de configuracion " + linea, 0);
        }
        return partes[1];
    }
    
    private static void configurar(File configFile) throws ParseException, FileNotFoundException {
        
        if (!configFile.canRead()) {
            throw new FileNotFoundException();
        }
        
        FileReader fr = null;
        fr = new FileReader(configFile);
        BufferedReader br = new BufferedReader(fr);
        
        try {
            String linea;
            String dato;
            
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            idEc = Integer.valueOf(dato);
            System.out.println("IdEC cargado:" + idEc + "\n");
            
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            numeroModem = Integer.valueOf(dato);
            System.out.println("IdEc modem cargado " + numeroModem+"\n");
            
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            carpetaParaModelos = dato;
            System.out.println("Carpeta de modelos cargada:"+carpetaParaModelos+"\n");
            
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            archivoDePuertosDeLaRed = dato;
            System.out.println("Archivo de Puertos de la red cargado:"+archivoDePuertosDeLaRed+"\n");
            
            ModeloTerminalRemota tr;
            linea = br.readLine();
            System.out.println("Comienzo Carga de terminales");
            while (linea != null) {
                tr = ModeloTerminalRemota.parse(linea);
                System.out.println("Terminal " + tr.toString() + " cargada\n");
                terminales.add(tr);
                linea = br.readLine();
            }
            System.out.println("Fin Carga Terminales");
        
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private static void conectarComponentes() {
        dataReceiver.setEntrada(trReceiverToData);
        dataReceiver.setSalidaValidator(dataToValidator);
        dataReceiver.setSalidaNetworkController(dataToNetwork);

        messageReceiver.setModemEntrada(dispatcherReceiver);
        messageReceiver.setModemSalida(entradaModem);
        messageReceiver.setSalida(trReceiverToData);

        modemDispatcher.setDataSalida(dispatcherReceiver);
        modemDispatcher.setModemEntrada(salidaModem);
        modemDispatcher.setStartupSalida(dispatcherNetwork);

        modemGSM.setEntrada(entradaModem);
        modemGSM.setSalida(salidaModem);

        networkController.setEntrada(dataToNetwork);
        networkController.setEntradaRaise(dispatcherNetwork);
        networkController.setModemSalida(entradaModem);

        validator.setEntradaDatos(dataToValidator);
        validator.setSalidaDatos(validatorToValidDataManager);

        validDataManager.setEntradaDatosInternos(validatorToValidDataManager);

        dataSender.setEntrada(pedidosDeSubsADatos);
        // TODO: falta la cola desde el validador
        dataSender.setSalidaAceptacionSubs(aceptacionSubsDatos);
        dataSender.setSalidaInfo(datosSalientes);

        subscriptorModelos.setEntradaRespuestaSubscriptor(respuestaSubs);
        subscriptorModelos.setSalidaSubscripcionesDatos(colaSubsDatos);
        subscriptorModelos.setSalidaSubscripcionesResultados(colaSubsResul);
        subscriptorModelos.setEntradaModelos(modelosSubscripcion);

        ecComunicator.setEntradaDatos(datosSalientes);
        ecComunicator.setEntradaResult(resultadosSalientes);
        ecComunicator.setSalidaSubscripExternasDatos(salidaSubscripExternasDatos);
        //ecComunicator.setEntradaRespSubscripExternasDatos();

        ecComunicator.setEntradaEnvioSubscripcionesDatos(colaSubsDatos);
        ecComunicator.setEntradaEnvioSubscripcionesResult(colaSubsResul);
        ecComunicator.setSalidaRespSubscripcionesResult(respuestaSubs);
        ecComunicator.setSalidaRespSubscripcionesDatos(respuestaSubs);

        ecComunicator.setSalidaDatosExternos(validatorToValidDataManager);
        //TODO: setear colas de informacion

        predictor.setSalidaSubscriptor(modelosSubscripcion);
        predictor.setSalidaResultManager(salidasResultados);

        System.out.println("[MAIN] Conecte los componentes");

    }

    private static void inicializarComponentes(){
        new File("resources").mkdir();
        File serverValidDataPath = new File("resources/ValidData.yap");
        serverValidDataPath.delete();

        File serverResultadosPath = new File("resources/Resultados.yap");
        serverResultadosPath.delete();

        File serverOutliersPath = new File("resources/Outliers.yap");
        serverOutliersPath.delete();

        try {
            serverValidDataPath.createNewFile();
            serverOutliersPath.createNewFile();
            serverResultadosPath.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
        validDataServer = Db4o.openServer(serverValidDataPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Valid Data en la ruta:" + serverValidDataPath.getAbsolutePath());
        
        outlierDataServer = Db4o.openServer(serverOutliersPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Outliers Data en la ruta:" + serverOutliersPath.getAbsolutePath());

        resultadosServer = Db4o.openServer(serverResultadosPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Resultados en la ruta:" + serverResultadosPath.getAbsolutePath());

        predictor = new PredictorManager(validDataServer, resultadosServer, carpetaParaModelos);
        System.out.println("Se creo el Predictor y se le le asigno el server de ValidData");
        
        validator = new ValidatorManager(outlierDataServer);
        System.out.println("Se creo el Validator y se le le asigno el server de ValidData");

        validDataManager = new ValidDataManager(validDataServer);
        System.out.println("Se creo el Validator y se le le asigno el server de ValidData");

        messageReceiver = new TRMessageReciever();
        System.out.println("Se creo TR Message Receiver");

        dataReceiver = new DataReceiver();
        System.out.println("Se creo el Data Receiver");

        modemDispatcher = new ModemDispatcher();
        System.out.println("Se creo el Modem Dispatcher");

        modemGSM = new ModemGSM(numeroModem);
        System.out.println("Se creo el Modem");

        networkController = new NetworkController();
        System.out.println("Se creo el Network Controller");

        dataSender = new DatosPublishSubscriber();
        System.out.println("Se creo el Data Sender");

        subscriptorModelos = new SubscriptorModelos();
        System.out.println("Se creo el Subscriptor de los Modelos");

        ecComunicator = new EcComunicator(archivoDePuertosDeLaRed);
        System.out.println("Se creo el EcComunicator");
    }

    private static void prenderComponentes() {
        System.out.println("Prendiendo Componentes");
        new Thread(validator).start();
        System.out.println("Se prendio el validador");

        new Thread(validDataManager).start();
        System.out.println("Se prendio el validDataManager");

        new Thread(predictor).start();
        System.out.println("Se prendio el predictor");

        modemDispatcher.start();
        System.out.println("Se prendio el modemDispatcher");

        modemGSM.start();
        System.out.println("Se prendio el modem");

        new Thread(messageReceiver).start();
        System.out.println("Se prendio el receiver");

        dataReceiver.start();
        System.out.println("Se prendio el data receiver");

        new Thread(networkController).start();
        System.out.println("Se prendio el network controller");

        new Thread(subscriptorModelos).start();
        System.out.println("Se prendio el subscriptor de modelos");

        new Thread(ecComunicator).start();
        System.out.println("Se prendio el subscriptor de modelos");

        //new Thread(dataSender).start();
        //System.out.println("Se prendio el Data Sender");

        System.out.println("Se prendieron todos los componentes");
    }

}
