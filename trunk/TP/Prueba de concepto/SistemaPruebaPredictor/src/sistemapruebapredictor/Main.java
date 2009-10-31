/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemapruebapredictor;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import predictor.PredictorManager;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class Main {

    private static PredictorManager predictor;
    private static CargadorDatos cargador;
    private static ObjectServer validDataServer;

    public static void main(String[] args) {
        inicializarComponentes();
        prenderComponentes();
    }

    private static void inicializarComponentes() {
        new File("resources").mkdir();
        File serverPath = new File("resources/ValidDataPrueba.yap");
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
        try {
            cargador = new CargadorDatos(validDataServer, new GeneradorDatosOrdenados(GeneradorDatosOrdenados.Orden.creciente, -10.0f, 1.0f, 30.0f));
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void prenderComponentes() {
        System.out.println("Prendiendo Componentes");
        new Thread(predictor).start();
        System.out.println("Se prendio el predictor");
        new Thread(cargador).start();
        System.out.println("Se prendio el cargador");
    }
}
