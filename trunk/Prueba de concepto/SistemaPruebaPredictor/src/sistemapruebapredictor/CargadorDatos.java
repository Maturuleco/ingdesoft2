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
import model.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class CargadorDatos implements Runnable {

    private static final long tiempoEspera = 500;
    private volatile boolean keepTrying = true;
    private ObjectServer server;
    private GeneradorDatos generador;

    public CargadorDatos(ObjectServer server, GeneradorDatos generador) {
        this.server = server;
        this.generador = generador;
    }

    @Override
    public void run() {
        DatoAlmacenado dato;
        while (keepTrying) {
            try {
                dato = generador.generarDato();
                escribirDatosValidos(dato);
                Thread.sleep(tiempoEspera);
            } catch (InterruptedException ex) {
                
            }
        }
    }

    public void escribirDatosValidos(DatoAlmacenado dato) {
        ObjectContainer cliente = server.openClient();
        try {
            cliente.store(dato);
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
