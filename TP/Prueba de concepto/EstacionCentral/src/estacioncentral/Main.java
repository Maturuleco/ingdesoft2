/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estacioncentral;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import predictor.PredictorManager;
import validator.ValidatorManager;

/**
 *
 * @author Santiago Avendaño
 */
public class Main {

    private static PredictorManager predictor;
    private static ValidatorManager validator;
    private static ObjectServer validDataServer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        inicializarComponentes();
        prenderComponentes();
    }

    private static void inicializarComponentes(){
        new File("resources").mkdir();
        File serverPath = new File("resources/ValidData.yap");
        serverPath.delete();
        try {
            serverPath.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
        validDataServer = Db4o.openServer(serverPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Valid Data en la ruta:" + serverPath.getAbsolutePath());
        predictor = new PredictorManager(validDataServer);
        System.out.println("Se creo el Predictor y se le le asigno el server de ValidData");
        validator = new ValidatorManager(validDataServer);
        System.out.println("Se creo el Validator y se le le asigno el server de ValidData");
    }

    private static void prenderComponentes() {
        System.out.println("Prendiendo Componentes");
        new Thread(predictor).run();
        new Thread(validator).run();
        System.out.println("Se prendieron todos los componentes");
    }

}
