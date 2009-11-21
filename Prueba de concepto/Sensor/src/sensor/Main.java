/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sensor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FactorClimatico;
/**
 *
 * @author matiaz
 */
public class Main {
    private static final int maxSize = 999999999;

    private static Integer name;
    private static FactorClimatico factor;
    private static long frequency;
    private static String directory;
    private static float dato = 0;

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

    public static String getInfo() {
       Date d = new Date();
       Float data = Float.valueOf(dato);
       String info = name.toString() + "|" +
                     factor.toString() + "|" +
                     data.toString() + "|" +
                     d.getTime();
       dato--;
       dato = dato%50;

        return info;
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
            if (!configFile.isFile() || !configFile.canRead()) {
                System.out.println("No se puede acceder al archivo de configuracion\n");
            } else {
                try {
                    FileReader fr = null;
                    fr = new FileReader(configFile);
                    BufferedReader br = new BufferedReader(fr);
                    String linea = br.readLine();
                    String dato = getDatoFromLine(linea);
  
                    name = Integer.valueOf(dato);
                    System.out.println("Nombre cargado\n");

                    linea = br.readLine();
                    dato = getDatoFromLine(linea);
                    factor = FactorClimatico.parse(dato);
                    System.out.println("Factor Climatico " + factor + " cargado\n");

                    linea = br.readLine();
                    dato = getDatoFromLine(linea);
                    frequency = Long.valueOf(dato);
                    System.out.println("Frecuencia " + frequency + " cargada\n");

                    linea = br.readLine();
                    dato = getDatoFromLine(linea);
                    directory = dato;
                    System.out.println("Directorio asignado: " + directory + " cargada\n");

                    File carpeta = new File(directory);
                    if (!carpeta.exists()) {
                        carpeta.mkdir();
                    }
                    int numSMS = 0;
                    String path = directory;
                    File folder = new File(directory);
                    while (true) {
                        try {
                            path = directory;
                            try {
                                File[] files = folder.listFiles();
                                // Solo se puede crear un mensaje nuevo
                                // si la carpeta no esta "llena"
                                if (files.length < 100) {
                                    path += "/" + "dato" + numSMS + ".txt";
                                    File file = new File(path);
                                    FileOutputStream fos = new FileOutputStream(file);
                                    String info = getInfo();
                                    for (char ch : info.toCharArray()) {
                                        fos.write(ch);
                                    }
                                    System.out.println("\nEl Sensor ha escrito: " + info);
                                    fos.close();
                                }
                                numSMS++;
                                if (numSMS > maxSize) {
                                    numSMS = 0;
                                }
                            } catch (Exception e) {
                                e.printStackTrace(System.err);
                            }
                            Thread.sleep(frequency);
                        } catch (InterruptedException ex) {
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}


