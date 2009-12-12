/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemapruebapredictor;


import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import evaluador.ResultadoEvaluacion;

/**
 *
 * @author Administrador
 */
public class CargadorResultados implements Runnable {
    private static final long tiempoEspera = 500;
    private volatile boolean keepTrying = true;
    private ObjectServer server;
    private GeneradorResultados generador;

    public CargadorResultados(ObjectServer server, GeneradorResultados generador) {
        this.server = server;
        this.generador = generador;
    }

    @Override
    public void run() {
        ResultadoEvaluacion resultado;
        while (keepTrying) {
            try {
                resultado = generador.generarDato();
                escribirDatosValidos(resultado);
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) {

            }
        }
    }

    public void escribirDatosValidos(ResultadoEvaluacion resultado) {
        ObjectContainer cliente = server.openClient();
        try {
            cliente.store(resultado);
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } catch (Db4oIOException e) {
            System.out.println("fallo la operacion de entrada salida");
            System.out.println(e.getMessage());
        } finally {
            cliente.close();
        }
    }
}
