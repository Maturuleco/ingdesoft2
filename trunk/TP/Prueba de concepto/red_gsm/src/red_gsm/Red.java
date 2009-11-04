/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package red_gsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class Red {

    public static final String path = "GSM";

    /**
     * @param args the command line arguments
     */
    public static boolean moveFile(File archivoAMover, String pathDestino, String nombreFinal) {
        File destino = new File(pathDestino, nombreFinal);
        return archivoAMover.renameTo(destino);
    }

    public static MensajeGSM readFile(File file) {
        FileReader fr = null;
        MensajeGSM mensaje = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String texto = br.readLine();

            System.out.println("La red lee :" + texto);
            fr.close();
            
            mensaje = MensajeGSM.parse(texto);
        } catch (ParseException ex) {
            Logger.getLogger(Red.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Red.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Red.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return mensaje;
    }

    public static void main(String[] args) {

        final int maxSize = 999999;
        Dictionary<Integer, File> modems = new Hashtable<Integer, File>();
        Dictionary<Integer, Integer> mensajeModems = new Hashtable<Integer, Integer>();
        File carpeta = new File(path);

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }


        MensajeGSM mensaje = null;
        int numeroDest;
        int numeroOrig;

        // recive
        while (true) {
            numeroDest = 0;
            numeroOrig = 0;
            String texto = "";
            try {
                File[] files = carpeta.listFiles();
                int longitud = 0;
                if (files != null) {
                    longitud = files.length;
                }

                for (int j = 0; j < longitud; j++) {
                    File file = files[j];
                    //System.out.println( getName()+ "D:" + file.getName());
                    if (file.canRead()) {
                        mensaje = readFile(file);
                        if (mensaje == null)
                            break;
                        // Computo el origen
                        numeroOrig = mensaje.getOrigen();
                        if (modems.get(numeroOrig) == null) {
                            File dirModem = new File(path + numeroOrig);
                            modems.put(numeroOrig, dirModem);
                            mensajeModems.put(numeroOrig, 0);
                        }

                        numeroDest = mensaje.getDestino();

                        if (modems.get(numeroDest) == null) {
                            File dirModem = new File(path + numeroDest);
                            modems.put(numeroDest, dirModem);
                            mensajeModems.put(numeroDest, 0);
                        }
                        // Si es mandado a mi lo borro

                        if (numeroDest == 0) {
                            file.delete();
                        } // y si conozco el destino lo muevo
                        else if (modems.get(numeroDest) != null) {
                            // Creo el nombre del archivo a mover
                            int numeroMsj = mensajeModems.get(numeroDest);
                            String nombre = numeroOrig + "-" + numeroMsj + ".txt";

                            String pathDestino = modems.get(numeroDest).getPath();

                            moveFile(file, pathDestino, nombre);

                            numeroMsj++;
                            if (numeroMsj >= maxSize) {
                                numeroMsj = 0;
                            }
                            mensajeModems.put(numeroDest, numeroMsj);
                        }
                    }
                }
            } catch  (Exception e) {
            e.printStackTrace(System.err);
            }
        }
    }

}
