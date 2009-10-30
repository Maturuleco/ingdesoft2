/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalremotafinal;

import Adapters.Adapter;
import DataManager.DataManager;
import DataSender.DataSender;
import StartUpManager.StartUpManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatoSensado;
import model.Mensaje;
import red_gsm.ModemGSM;
import red_gsm.MensajeGSM;
import red_gsm.ComparadorMsjGSM;

/**
 *
 * @author matiaz
 */
public class Main {

    public static int estacionCentral;
    public static int idTR;
    private static int numeroModem;
    private static List<Adapter> adapters = new LinkedList<Adapter>();
    private static DataSender dataSender;
    private static DataManager dataManager;
    private static StartUpManager startUpManager;
    private static ModemDispatcher modemDispatcher;
    private static ModemGSM modemGSM;
    private static BlockingQueue<DatoSensado> adapterManager =
            new LinkedBlockingQueue<DatoSensado>();
    private static BlockingQueue<Mensaje> managerSender =
            new LinkedBlockingQueue<Mensaje>();
    private static BlockingQueue<Mensaje> senderManager =
            new LinkedBlockingQueue<Mensaje>();
    private static BlockingQueue<MensajeGSM> salidaModem =
            new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> entradaModem =
            new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> dispatcherSender =
            new LinkedBlockingQueue<MensajeGSM>();

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

    private static Adapter getAdapter(String dato) throws ParseException {
        String[] parte = dato.split("\\|", 2);
        Adapter adapter;
        if (parte[0].equalsIgnoreCase("default")) {
            adapter = Adapter.parse(parte[1]);
        } else {
            throw new ParseException(dato, 1);
        }
        return adapter;
    }

    private static void configurar(File configFile) throws ParseException, FileNotFoundException {

        if (!configFile.canRead()) {
            throw new FileNotFoundException();
        }
        FileReader fr = null;
        fr = new FileReader(configFile);
        BufferedReader br = new BufferedReader(fr);
        try {
            String linea = br.readLine();
            String dato = getDatoFromLine(linea);
            idTR = Integer.valueOf(dato);
            System.out.println("IdTR cargado\n");
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            numeroModem = Integer.valueOf(dato);
            System.out.println("Numero de Modem " + numeroModem + "\n");
            linea = br.readLine();
            dato = getDatoFromLine(linea);
            estacionCentral = Integer.valueOf(dato);
            System.out.println("Numero Estacion Central " + estacionCentral + "\n");
            Adapter adapter;
            linea = br.readLine();
            System.out.println("Comienzo Carga de adapters");
            while (linea != null) {
                dato = getDatoFromLine(linea);
                adapter = getAdapter(dato);
                System.out.println("Adapter " + adapter.getName() + " cargado\n");
                adapters.add(adapter);
                linea = br.readLine();
            }
            System.out.println("Fin Carga Adapters");
       //     configFile.delete();
            linea = br.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private static void crearComponentes() {
        modemGSM = new ModemGSM(numeroModem);
        modemDispatcher = new ModemDispatcher();
        startUpManager = new StartUpManager();
        dataSender = new DataSender();

        //TODO: revisar lo de abajo
        dataManager = new DataManager();
        // Los adapters se crean en la config...

    }

    private static void conectarComponentes() {
        for (Adapter adapter : adapters) {
            adapter.setSalida(adapterManager);
        }
        dataManager.setEntrada(adapterManager);
        dataManager.setSalida(managerSender);

        dataSender.setEntrada(managerSender);
        dataSender.setSalida(senderManager);
        dataSender.setModemEntrada(dispatcherSender);
        dataSender.setModemSalida(entradaModem);

        modemDispatcher.setDataSalida(dispatcherSender);
        modemDispatcher.setModemEntrada(salidaModem);

        modemGSM.setEntrada(entradaModem);
        modemGSM.setSalida(salidaModem);

        startUpManager.setSalida(entradaModem);

    }

    /*
    private static List<Adapter> adapters = new LinkedList<Adapter>();
    private static DataSender dataManager;
    private static DataManager dataManager;
    private static StartUpManager startUpManager;
    private static ModemDispatcher modemDispatcher;
    private static ModemGSM modemGSM;
     */
    private static void prenderComponentes() {
        System.out.println("Comenzando ejecucion de componentes\n");
        modemGSM.start();
        System.out.println("Comenzando ejecucion de Modem\n");
        modemDispatcher.start();
        System.out.println("Comenzando ejecucion de Dispatcher\n");
        dataManager.start();
        System.out.println("Comenzando ejecucion de Data Manager\n");
        dataSender.start();
        System.out.println("Comenzando ejecucion de Data Sender\n");
        for (Adapter adapter : adapters) {
            System.out.println("Comenzando ejecucion de Adapter "+adapter.getName().toString()+"\n");
            adapter.start();
        }
        startUpManager.start();
    }

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
                } catch (ParseException ex) {
                    System.out.println("Archivo de configuracion invalido\n");
                } catch (FileNotFoundException ex) {
                    System.out.println("Path invalido\n");
                }
                System.out.println("Inicializando TR" + idTR + "\n");
                System.out.println("Creando componentes\n");
                crearComponentes();
                System.out.println("Conectando componentes\n");
                conectarComponentes();
                System.out.println("Prendiendo componentes\n");
                prenderComponentes();
                System.out.println("Fin Inicializacion\n");

            }
        }

    }
}
