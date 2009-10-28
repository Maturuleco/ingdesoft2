/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package redTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import red_gsm.MensajeGSM;
import red_gsm.MensajeToModemGSM;
import red_gsm.ModemGSM;

/**
 *
 * @author matiaz
 */
public class TestDeRedGSM {
    private static ModemGSM[] modems = new ModemGSM[11];
    private static MensajeToModemGSM[] mensajes = new MensajeToModemGSM[10];
    
    private static BlockingQueue<MensajeGSM> salida1 = new LinkedBlockingQueue<MensajeGSM>();
    /*
    private static BlockingQueue<MensajeGSM> salida2 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida3 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida4 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida5 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida6 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida7 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida8 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida9 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida10 = new LinkedBlockingQueue<MensajeGSM>();
    private static BlockingQueue<MensajeGSM> salida11 = new LinkedBlockingQueue<MensajeGSM>();
    */

    private static BlockingQueue<MensajeToModemGSM> entrada1 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada2 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada3 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada4 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada5 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada6 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada7 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada8 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada9 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada10 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();
    private static BlockingQueue<MensajeToModemGSM> entrada11 =
                                new LinkedBlockingQueue<MensajeToModemGSM>();


    public TestDeRedGSM() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        for (int i = 0; i < 11; i++) {
            modems[i] = new ModemGSM(100+i);
            modems[i].setEntrada(null);
        }
        modems[1].setEntrada(entrada1);
        modems[2].setEntrada(entrada2);
        modems[3].setEntrada(entrada3);
        modems[4].setEntrada(entrada4);
        modems[5].setEntrada(entrada5);
        modems[6].setEntrada(entrada6);
        modems[7].setEntrada(entrada7);
        modems[8].setEntrada(entrada8);
        modems[9].setEntrada(entrada9);
        modems[10].setEntrada(entrada10);
        modems[0].setEntrada(entrada11);

        modems[1].setSalida(salida1);
        modems[2].setSalida(salida1);
        modems[3].setSalida(salida1);
        modems[4].setSalida(salida1);
        modems[5].setSalida(salida1);
        modems[6].setSalida(salida1);
        modems[7].setSalida(salida1);
        modems[8].setSalida(salida1);
        modems[9].setSalida(salida1);
        modems[10].setSalida(salida1);
        modems[0].setSalida(salida1);

        for (int i = 0; i < mensajes.length; i++) {
            mensajes[i] = new MensajeToModemGSM(100+i, "Hola Manola "+i+"\n");
        }


    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSimple() {
        for (int i = 0; i < modems.length; i++) {
            modems[i].start();
        }
        for (int i = 0; i < mensajes.length; i++) {
            try {
                entrada11.put(mensajes[i]);
            } catch (InterruptedException ex) { }
            
        }

        while (true) {
            try {
                System.out.println(salida1.take().toString());
            } catch (InterruptedException ex) { }
        }

    }

}