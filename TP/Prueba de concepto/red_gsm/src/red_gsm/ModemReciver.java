/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class ModemReciver extends Thread {

    private static final long timeToWait = 10;
    private static final int bufferSize = 1000000;
    private File folderPropia;
    private BlockingQueue<MensajeGSM> salida;

    public ModemReciver(File folderPropia) {
        this.folderPropia = folderPropia;
    }

    public void setSalida(BlockingQueue<MensajeGSM> salida) {
        this.salida = salida;
    }

    private void recive() {

            File[] files = folderPropia.listFiles();
            int longitud = 0;
            if (files != null)
                longitud = files.length;
            for (int j = 0; j < longitud; j++) {
                File file = files[j];
                //System.out.println( getName()+ "D:" + file.getName());
                if(file.canRead()){
                FileReader fr = null;
                try {
                    fr = new FileReader(file);
                    
                    BufferedReader br = new BufferedReader(fr, bufferSize);
                    String texto = "";
                    String linea;
                    while ( (linea= br.readLine()) != null) {
                        texto += linea;
                    }
                    
                    fr.close();
                    file.delete();

                    MensajeGSM mensaje = MensajeGSM.parse(texto);
                    salida.put(mensaje);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ModemReciver.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    System.out.println("\nERROR GSM\tNo se puede Parsear: "+ex.toString());
                    Logger.getLogger(ModemReciver.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ModemReciver.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        fr.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ModemReciver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                }

            }

    }

    @Override
    public void run() {
        while (true){
            recive();
            try {
                sleep(timeToWait);
            } catch (InterruptedException ex) {
                Logger.getLogger(ModemReciver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

}
