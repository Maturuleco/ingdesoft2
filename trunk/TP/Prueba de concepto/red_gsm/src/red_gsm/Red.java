/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 *
 * @author tas
 */
public class Red {
    public static final String path = "GSM";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final int maxSize = 999999;
        Dictionary< Integer , File > modems = new Hashtable< Integer, File >();
        Dictionary< Integer , Integer > mensajeModems = new Hashtable< Integer, Integer >();
        File carpeta = new File (path);
        
        if (!carpeta.exists())
            carpeta.mkdir();
        
        Boolean hayMensaje;
        MensajeGSM mensaje = null;
        int numeroDest;
        int numeroOrig;
    
        // recive
        while (true) {
            hayMensaje = false;
            numeroDest = 0;
            numeroOrig = 0;
            while (! hayMensaje) {
                // Recive
                try {
                    File[] files = carpeta.listFiles();
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
                            
                            //Lo parseo
                            mensaje = MensajeGSM.parse(texto);
                            // Computo el origen
                            numeroOrig = mensaje.getOrigen();
                            if (modems.get(numeroOrig) == null){
                                File dirModem = new File (path+numeroOrig);
                                modems.put(numeroOrig, dirModem);
                                mensajeModems.put(numeroOrig, 0);
                            }
                            // Me aseguro de que lo pueda mandar
                            numeroDest = mensaje.getDestino();
                            // Si es mandado a mi o si conozco el destino 
                            // ya sé que lo voy a poder mandar, lo borro
                            if (numeroDest == 0 || modems.get(numeroDest) != null) {
                                fr.close();
                                file.delete();
                                if (numeroDest != 0) {
                                    hayMensaje = true;
                                    break;
                                }
                            }
                            fr.close();
                        }

                    }
                } catch (Exception e) {
                      e.printStackTrace(System.err);
                }
            }
            
            int numeroMsj = mensajeModems.get(numeroDest);
            
            // Send
            String pathDestino = modems.get(numeroDest).getPath();

            String phrase = mensaje.toString();

            try {
                pathDestino +=  "/" + numeroOrig + "-" + numeroMsj +".txt";
                //System.out.println(getName() + " C: " +"sms"+ j +".txt");

                File file = new File(pathDestino);
                FileOutputStream fos = new FileOutputStream(file);

                for (char ch : phrase.toCharArray()) {
                    fos.write(ch);
                }
                fos.close();
                numeroMsj++;
                if (numeroMsj >= maxSize)
                    numeroMsj = 0;
                mensajeModems.put(numeroDest, numeroMsj);

            } catch (Exception e) {
                      e.printStackTrace(System.err);
            }
        }
    
    }

}
