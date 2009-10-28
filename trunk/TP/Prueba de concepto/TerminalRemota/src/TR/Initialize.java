/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import threadcomunication.Sensor;

/**
 *
 * @author matiaz
 */
public class Initialize {
    public static int estacionCentral;
    public static int TRid;
    private static int numeroModem;
    private static Sensor[] sensores;

    private static String getDatoFromLine(String linea) throws ParseException {
        if (linea == null)
            throw new ParseException("Configuration file", 0);
        String[] partes = linea.split(":");
        if (partes.length != 2)
            throw new ParseException("En archivo de configuracion "+linea, 0);
        return partes[1];
    }

    private static boolean configurar(File configFile) throws ParseException {

        FileReader fr = null;
        try {
            if (!configFile.canRead()) {
                return false;
            }
            fr = new FileReader(configFile);
            BufferedReader br = new BufferedReader(fr);

            String linea = br.readLine();
            String dato = getDatoFromLine(linea);
            TRid = Integer.valueOf(dato);

            linea = br.readLine();
            dato = getDatoFromLine(linea);
            numeroModem = Integer.valueOf(dato);

            linea = br.readLine();
            dato = getDatoFromLine(linea);
            estacionCentral = Integer.valueOf(dato);
            
            while (linea != null) {
                // fr.close();
                configFile.delete();
                linea = br.readLine();
            }
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Initialize.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1)
            System.out.println("Es necesario el archivo de configuracion\n");
        else {
            String configPath = args[0];
            File configFile = new File(configPath);
            if (! configFile.isFile())
                System.out.println("Es necesario el archivo de configuracion\n");
            else {
                configurar(configFile);
            }
        }
        
    }

}
