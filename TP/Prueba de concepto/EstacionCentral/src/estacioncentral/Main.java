/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estacioncentral;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import dataReceiver.DataReceiver;
import java.io.File;
import java.io.IOException;
import messageReceiver.TRMessageReciever;
import networkController.NetworkController;
import predictor.PredictorManager;
import validator.ValidatorManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import model.DatoAlmacenado;
import model.Mensaje;
import networkController.HeartbeatMessege;
import red_gsm.ComparadorMsjGSM;
import red_gsm.MensajeGSM;
import red_gsm.ModemGSM;

/**
 *
 * @author Santiago Avendaño
 */
public class Main {

    public static int numeroModem = 999;
    private static int trRegistradas = 10;

    private static PredictorManager predictor;
    private static ValidatorManager validator;
    private static DataReceiver dataReceiver;
    private static TRMessageReciever messageReceiver;
    private static NetworkController networkController;
    private static ModemDispatcher modemDispatcher;
    private static ModemGSM modemGSM;
    
    private static BlockingQueue<Mensaje> trReceiverToData =
            new LinkedBlockingQueue<Mensaje>();
    private static BlockingQueue<DatoAlmacenado> dataToValidator =
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


    private static ObjectServer validDataServer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        inicializarComponentes();
        conectarComponentes();
        prenderComponentes();
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

    }

    private static void inicializarComponentes(){
        new File("resources").mkdir();
        File serverPath = new File("resources/ValidData.yap");
        serverPath.delete();
        try {
            serverPath.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
        validDataServer = Db4o.openServer(serverPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Valid Data en la ruta:" + serverPath.getAbsolutePath());
        predictor = new PredictorManager(validDataServer);
        System.out.println("Se creo el Predictor y se le le asigno el server de ValidData");
        validator = new ValidatorManager(validDataServer);
        System.out.println("Se creo el Validator y se le le asigno el server de ValidData");

        messageReceiver = new TRMessageReciever();
        System.out.println("Se creo TR Message Receiver");

        dataReceiver = new DataReceiver();
        System.out.println("Se creo el Data Receiver");

        modemDispatcher = new ModemDispatcher();
        System.out.println("Se creo el Modem Dispatcher");

        modemGSM = new ModemGSM(numeroModem);
        System.out.println("Se creo el Modem");

        networkController = new NetworkController(trRegistradas);
        System.out.println("Se creo el Network Controller");

    }

    private static void prenderComponentes() {
        System.out.println("Prendiendo Componentes");
        new Thread(validator).start();
        System.out.println("Se prendio el validador");

//        new Thread(predictor).start();
//        System.out.println("Se prendio el predictor");

        modemDispatcher.start();
        System.out.println("Se prendio el modemDispatcher");

        modemGSM.start();
        System.out.println("Se prendio el modem");

        messageReceiver.start();
        System.out.println("Se prendio el receiver");

        dataReceiver.start();
        System.out.println("Se prendio el data receiver");

//        networkController.start();
//        System.out.println("Se prendio el network controller");


//        System.out.println("Se prendieron todos los componentes");
    }

}
