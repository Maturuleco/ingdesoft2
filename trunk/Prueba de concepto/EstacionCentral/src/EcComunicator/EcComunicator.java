/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EcComunicator;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tas
 */
public class EcComunicator {
    ControladorPuertos puertosEcs;
    
    

    public boolean configurar(String pathPortsEcs) {
        File configFile = new File(pathPortsEcs);
        boolean res = false;
        if (!configFile.isFile() || !configFile.canRead()) {
            System.out.println("Archivo de Puertos Inaccesible");
        }
        else {
            FileInputStream fis = null;
            try {
                configFile.setReadOnly();
                XStream xstream = new XStream();
                fis = new FileInputStream(configFile);

                puertosEcs = (ControladorPuertos) xstream.fromXML(fis);
                res = true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EcComunicator.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    configFile.setWritable(true);
                    fis.close();
                } catch (IOException ex) {
                    Logger.getLogger(EcComunicator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return res;
    }
    
}