package sensorandadaptercomunication;

import java.io.File;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.FactorClimatico;

public class Main {

    public static void main(String[] args) {
         for (int i = 1; i <= 3; ++i){
            try {
                String path = "C:/Documents and Settings/Lionel Raymundi/Escritorio/sensor" + i + "/";

                File carpeta = new File(path);

                if (!carpeta.exists()) {
                    carpeta.mkdir();
                }
                FactorClimatico factor = FactorClimatico.getFactor();

                Sensor s = new Sensor(path, "sensor" + i, factor, 10 * i);
                s.start();
                Adapter a = new Adapter(path, "sensor" + i, 20 * i);
                a.start();
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         }
            
    }

}
