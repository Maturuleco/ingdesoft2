package estacioncentral;

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
import model.Mensaje;
import EstadoDeRed.HeartbeatMessege;
import ModeloTerminal.ModeloTerminalRemota;
import ValidDataManager.ValidDataManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
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
    
    private static BlockingQueue<Mensaje> trReceiverToData = new LinkedBlockingQueue<Mensaje>();
    private static BlockingQueue<DatoAlmacenado> dataToValidator = new LinkedBlockingQueue<DatoAlmacenado>();
    private static BlockingQueue<DatoAlmacenado> validatorToValidDataManager = new LinkedBlockingQueue<DatoAlmacenado>();
    private static BlockingQueue<HeartbeatMessege> dataToNetwork = new LinkedBlockingQueue<HeartbeatMessege>();
    private static BlockingQueue<MensajeGSM> salidaModem = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> entradaModem = new PriorityBlockingQueue<MensajeGSM>(3, new ComparadorMsjGSM());
    private static BlockingQueue<MensajeGSM> dispatcherReceiver = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> dispatcherNetwork = new LinkedBlockingQueue<MensajeGSM>();


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
            System.out.println("IdEc cargado\n");
            
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            numeroModem = Integer.valueOf(dato);
            System.out.println("IdEc cargado\n");
            
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

        validator.setEntradaDatos(dataToValidator);
        validator.setSalidaDatos(validatorToValidDataManager);
        validDataManager.setEntradaDatosInternos(validatorToValidDataManager);

        // OJO! FALTA la cola del EC-COMUNICATOR AL VALIDDATAMANAGER (externos)

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


        System.out.println("Se prendieron todos los componentes");
    }

}
