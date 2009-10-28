/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

import java.io.File;

/**
 *
 * @author matiaz
 */
public class Initialize {
    public static int estacionCentral = 0;
    public static int TRid;
    private static int numeroModem;

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
                
            }
        }
        
    }

}
