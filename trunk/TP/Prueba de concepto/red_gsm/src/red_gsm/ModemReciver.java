/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class ModemReciver extends Thread {

    private static final long timeToWait = 100;
    private File folderPropia;
    private BlockingQueue<MensajeGSM> salida;

    public ModemReciver(File folderPropia) {
        this.folderPropia = folderPropia;
    }

    public void setSalida(BlockingQueue<MensajeGSM> salida) {
        this.salida = salida;
    }

    private void recive() {
        try {
            File[] files = folderPropia.listFiles();
            int longitud = 0;
            if (files != null)
                longitud = files.length;
            for (int j = 0; j < longitud; j++) {
                File file = files[j];
                //System.out.println( getName()+ "D:" + file.getName());
                if(file.canRead()){
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    String texto = br.readLine();
                    // fr.close();
                    file.delete();
                    
                    MensajeGSM mensaje = MensajeGSM.parse(texto);
                    salida.put(mensaje);
                }

            }
        } catch (Exception e) {
              e.printStackTrace(System.err);
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
