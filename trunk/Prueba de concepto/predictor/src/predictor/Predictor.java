/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public abstract class Predictor implements Runnable {

    private static final String NOMBRE_DIRECTORIO_PREDICCIONES = "Predicciones";
    private static final String PREFIJO_ARCHIVO_ALERTA = "Alerta";

    protected String lugar;
    protected Regla regla;

    public Predictor(){
        
    }

    public Predictor(Regla regla, String lugar) {
        this.lugar = lugar;
        this.regla = regla;
    }

    public abstract Boolean analizar();

    @Override
    public void run() {
        Boolean detectoAlerta = analizar();
        if (detectoAlerta) {
            escribirPrediccion();
            System.out.println(Calendar.getInstance().getTime().toString());
            System.out.println("<<" + regla.getMensajePrediccion()+ ">>");
        } else {
            System.out.println("<<NO " + regla.getMensajePrediccion()+">>");
        }
    }

    private void escribirPrediccion() {
        String nombreArchivo = obtenerNombreArchivoPrediccion();
        escribirPrediccionEnArchivo(nombreArchivo);
    }

    private String obtenerNombreArchivoPrediccion(){
        Date ahora = Calendar.getInstance().getTime();
        String nombre = PREFIJO_ARCHIVO_ALERTA;
        nombre += String.valueOf(ahora.getTime());
        nombre += lugar;
        nombre +=  regla.getMensajePrediccion();
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
            salida.append("Lugar: " + lugar + "\n");
            salida.append("Mensaje: " + regla.getMensajePrediccion() + "\n");
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
