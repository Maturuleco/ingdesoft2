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
import Datos.FactorClimatico;
import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import modelo.Modelo;
import predictor.PredictorManager;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class Main {

    private static PredictorManager predictor;
    private static CargadorDatos cargador1;
    private static CargadorDatos cargador2;
    private static CargadorDatos cargador3;
    private static CargadorResultados cargadorRes1;
    private static CargadorResultados cargadorRes2;
    private static ObjectServer validDataServer;
    private static ObjectServer resultadosServer;

    public static void main(String[] args) {
        inicializarComponentes();
        prenderComponentes();
    }

    private static void inicializarComponentes() {
        new File("resources").mkdir();
        File serverValidDataPath = new File("resources/ValidDataPrueba.yap");
        serverValidDataPath.delete();
        File serverResultadosPath = new File("resources/Resultados.yap");
        serverResultadosPath.delete();
        try {
            serverValidDataPath.createNewFile();
            serverResultadosPath.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
        validDataServer = Db4o.openServer(serverValidDataPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Valid Data en la ruta:" + serverValidDataPath.getAbsolutePath());
        resultadosServer = Db4o.openServer(serverResultadosPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base Resultados en la ruta:" + serverResultadosPath.getAbsolutePath());
        predictor = new PredictorManager(validDataServer, resultadosServer, "../Configuraciones/ModelosPruebaPredictor");
        predictor.setSalidaResultManager(new LinkedBlockingQueue<Collection<ResultadoEvaluacion>>());
        predictor.setSalidaSubscriptor(new LinkedBlockingQueue<Modelo>());
        System.out.println("Se creo el Predictor y se le le asigno el server de ValidData");
        try {
            cargador1 = new CargadorDatos(validDataServer, new GeneradorDatosOrdenados(FactorClimatico.temperatura, GeneradorDatosOrdenados.Orden.creciente, -10.0f, 1.0f, 30.0f, 1, 1));
            cargador2 = new CargadorDatos(validDataServer, new GeneradorDatosOrdenados(FactorClimatico.temperatura, GeneradorDatosOrdenados.Orden.decreciente, -10.0f, 1.0f, -30.0f, 3, 1));
            cargador3 = new CargadorDatos(validDataServer, new GeneradorDatosOrdenados(FactorClimatico.humedad, GeneradorDatosOrdenados.Orden.decreciente, 50.0f, 1.0f, 30.0f, 3, 1));
            cargadorRes1 = new CargadorResultados(resultadosServer, new GeneradorResultados(GeneradorResultados.Orden.creciente, 0, 1, 10, 1, 1));
            cargadorRes2 = new CargadorResultados(resultadosServer, new GeneradorResultados(GeneradorResultados.Orden.creciente, 0, 1, 10, 2, 4));
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void prenderComponentes() {
        System.out.println("Prendiendo cargadores");
        new Thread(predictor).start();
        System.out.println("Se prendio el predictor");
        new Thread(cargador1).start();
        System.out.println("Se prendio el cargador1");
        new Thread(cargador2).start();
        System.out.println("Se prendio el cargador2");
        new Thread(cargador3).start();
        System.out.println("Se prendio el cargador3");
        new Thread(cargadorRes1).start();
        System.out.println("Se prendio el cargador de resultados 1");
        new Thread(cargadorRes2).start();
        System.out.println("Se prendio el cargador de resultados 2");
        System.out.println("Se prendieron los cargadores");
    }
}
