/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package terminalremotafinal;

import DataSender.DataSender;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataSource;
import model.DatoSensado;
import model.FactorClimatico;
import model.Mensaje;
import red_gsm.MensajeGSM;

/**
 *
 * @author Administrador
 */
public class testMain {

    /**
     * @param args the command line arguments
     */
    public static void adic(Integer num) {
        num++;
    }

    public static void formatPartes(String[] partes) {
        for (int i = 0; i < partes.length-1; i++) {
            String msj = partes[i];
            // Le pongo un Id al Mensaje en su cuerpo
            // Tb le pongo una M (more) para decir que
            // no es la última fracción
            msj = "M#"+i+"#"+msj;
            partes[i] = msj;
        }
        // Hago uno para el último
        int i = partes.length - 1;
        String msj = partes[i];
        // L de Last
        msj = "L#"+i+"#"+msj;
        partes[i] = msj;
    }
    
    public static void testDataSender() {


        BlockingQueue<MensajeGSM> modemSalida = new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> modemEntrada = new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<Mensaje> salida = new LinkedBlockingQueue<Mensaje>();
        BlockingQueue<Mensaje> entrada = new LinkedBlockingQueue<Mensaje>();

        Mensaje m1 = new Mensaje(01, DataSource.estacion_central);

        DatoSensado d1 = new DatoSensado(new Integer(001), new Date(), FactorClimatico.humedad, new Float(4.07));
        DatoSensado d2 = new DatoSensado(new Integer(002), new Date(), FactorClimatico.humedad, new Float(3.57));
        DatoSensado d3 = new DatoSensado(new Integer(005), new Date(), FactorClimatico.presion, new Float(18.7));
        DatoSensado d4 = new DatoSensado(new Integer(001), new Date(), FactorClimatico.humedad, new Float(4.57));
        DatoSensado d5 = new DatoSensado(new Integer(003), new Date(), FactorClimatico.temperatura, new Float(21.57));

        m1.addDato(d1);
        m1.addDato(d2);
        m1.addDato(d3);
        m1.addDato(d4);
        m1.addDato(d5);

        DataSender dataSender = new DataSender();
        dataSender.setEntrada(entrada);
        dataSender.setModemEntrada(modemEntrada);
        dataSender.setModemSalida(modemSalida);
        dataSender.setSalida(salida);

        //TODO: ver que prioridad hay que setearle
        MensajeGSM respuesta = new MensajeGSM(0, 01, "ACK" +"#" + 0 +"#"+ String.valueOf(m1.getTimeStamp()) +"#"+"firmaEC", 0);

        System.out.println("\nComenzando Test Data Sender\nSe inicia el data Sender");
        dataSender.start();
        System.out.println("\nSe envia el Mensaje");
        try {
            entrada.put(m1);
        } catch (InterruptedException ex) {
            System.out.println("\nLa cola no aceptó el mensaje");
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            MensajeGSM msjToModem = modemSalida.take();
            System.out.println("\nSe recibe el Mensaje");
            String cuerpo = msjToModem.getMensaje();
            String[] cachos = cuerpo.split("#");
            Mensaje m1bis = Mensaje.parse(cachos[2]);
            assert m1.equals(m1bis) : "\nLos mensajes son distintos" ;
        } catch (ParseException ex) {
            System.out.println("\nNo se puede parsear el mensaje");
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            System.out.println("\nLa cola no me deja tomar el mensaje");
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\nSe envia la respuesta");
        try {
            modemEntrada.put(respuesta);
        } catch (InterruptedException ex) {
            System.out.println("\nLa cola no aceptó el mensaje");
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("\nFin envio de respuesta");
        
        try {
            Mensaje m1bisbis = salida.take();
            System.out.println("\nSe recibe el mensaje de confirmacion de envio");
            assert m1.equals(m1bisbis) : "\nLos mensajes son distintos" ;
        } catch (InterruptedException ex) {
            System.out.println("\nLa cola no me deja tomar el mensaje");
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\nFin Test, Test OK");
    }
    
    public static void testMensaje() {
        
        Mensaje m1 = new Mensaje(01, DataSource.estacion_central);

        DatoSensado d1 = new DatoSensado(new Integer(001), new Date(), FactorClimatico.humedad, new Float(4.07));
        DatoSensado d2 = new DatoSensado(new Integer(002), new Date(), FactorClimatico.humedad, new Float(3.57));
        DatoSensado d3 = new DatoSensado(new Integer(005), new Date(), FactorClimatico.presion, new Float(18.7));
        DatoSensado d4 = new DatoSensado(new Integer(001), new Date(), FactorClimatico.humedad, new Float(4.57));
        DatoSensado d5 = new DatoSensado(new Integer(003), new Date(), FactorClimatico.temperatura, new Float(21.57));

        m1.addDato(d1);
        m1.addDato(d2);
        m1.addDato(d3);
        m1.addDato(d4);
        m1.addDato(d5);

        System.out.println("\n Test Mensaje");
       try {
            System.out.println("\n Mensaje: \n\t");
            String msj = m1.toString();
            System.out.println(msj);
            System.out.println("\n Parseo de Mensaje...");

            Mensaje m2 = Mensaje.parse(msj);
            assert m1.equals(m2) : "\nLos mensajes no son iguales" ;
        } catch (ParseException ex) {
            Logger.getLogger(testMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("\n Fin Test Mensaje");
    }
    
    public static String[] fraccionar(String msj) {
        // TODO: los msj SMS tienen 160 caracteres!
        String[] partes = new String[1];
        partes[0] = msj;
        return partes;
    }
    
    public static void main(String[] args) {

        String res = "Hola";
        res += " Manola";
        System.out.println(res);
        Integer qonda = new Integer(33);
        Integer hola = qonda + 1;
        System.out.println(qonda);
        System.out.println(hola);
        adic(qonda);
        System.out.println((0-1+6)%6);
        
        String[] test = new String[3];
        test[0] = "hola";
        test[1] = "chau";
        test[2] = "adios";
        formatPartes(test);
        System.out.println("\n Array de String: \n");
        System.out.println("[");
        for (String string : test) {
            System.out.println(string+",");
        }
        System.out.println("]\n");
        
        System.out.println("Test Fraccionar");
        String [] fraccionado = fraccionar("Fraccionate Este");
        System.out.println("Fin Test Fraccionar");
        
        testMensaje();
        
        testDataSender();

        
    }
    
}
