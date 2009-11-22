/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import predictor.Predictor;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorThread extends Thread {

    private static final String NOMBRE_DIRECTORIO_PREDICCIONES = "Predicciones";
    private static final String PREFIJO_ARCHIVO_ALERTA = "Alerta";

    private Predictor predictor;
    private Contador contador;

    public PredictorThread(Predictor predictor, Contador contador) {
        this.predictor = predictor;
        this.contador = contador;
    }

    @Override
    public void run() {
        if (predictor.analizar()) {
            contador.incrementar();
        }
        System.out.println("Finalice de evaluar regla:" + predictor.getRegla().getNombre());
    }

     private void escribirPrediccion() {
        String nombreArchivo = obtenerNombreArchivoPrediccion();
        escribirPrediccionEnArchivo(nombreArchivo);
    }

    private String obtenerNombreArchivoPrediccion(){
        Date ahora = Calendar.getInstance().getTime();
        String nombre = PREFIJO_ARCHIVO_ALERTA;
        nombre += String.valueOf(ahora.getTime());
        return nombre;
    }

    private void escribirPrediccionEnArchivo(String nombreArchivo){
         try {
            new File(NOMBRE_DIRECTORIO_PREDICCIONES).mkdir();
            FileWriter fw = new FileWriter(NOMBRE_DIRECTORIO_PREDICCIONES+"/"+nombreArchivo+".txt");
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.append(" ============ ALERTA ============\n");
            salida.append("Fecha: " + timeStamp().toString() + "\n");
            salida.append(" ================================");
            salida.close();
        } catch (java.io.IOException ioex) {
            System.out.println("se presento el error: " + ioex.toString());
        }
    }

    private Date timeStamp(){
        return Calendar.getInstance().getTime();
    }
}
