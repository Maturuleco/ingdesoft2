/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author matiaz
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ModemGSM[] modems = new ModemGSM[11];
        MensajeGSM[] mensajes = new MensajeGSM[10];

        BlockingQueue<MensajeGSM> salida1 = new LinkedBlockingQueue<MensajeGSM>();
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

        BlockingQueue<MensajeGSM> entrada1 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada2 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada3 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada4 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada5 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada6 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada7 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada8 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada9 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada10 =
                                    new LinkedBlockingQueue<MensajeGSM>();
        BlockingQueue<MensajeGSM> entrada11 =
                                    new LinkedBlockingQueue<MensajeGSM>();


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
            mensajes[i] = new MensajeGSM(0, 100+i, "Hola Manola "+i+"\n");
        }

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
