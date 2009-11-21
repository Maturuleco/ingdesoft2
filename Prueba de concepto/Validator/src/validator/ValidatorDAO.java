/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import java.util.Collection;
import java.util.List;
import model.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño
 */
public class ValidatorDAO {

    private ObjectServer server;

    public ValidatorDAO(ObjectServer server) {
        this.server = server;
    }

    public void escribirDatosValidos(Collection<DatoAlmacenado> datos) {
        ObjectContainer cliente = server.openClient();
        try {
            for (DatoAlmacenado datoAlmacenado : datos) {
            //System.out.println("\nBASE\tSe escribe en la base de Datos:\n"+datoAlmacenado.mostrar()+"\n");
                cliente.store(datoAlmacenado);
            }
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } finally {
            cliente.close();
        }
    }


    public void escribirDatosValidos(DatoAlmacenado dato) {
        ObjectContainer cliente = server.openClient();
        try {
            cliente.store(dato);
            //System.out.println("\nBASE\tSe escribe en la base de Datos:\n"+dato.mostrar()+"\n");
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } catch(Db4oIOException e){
            System.out.println("fallo la operacion de entrada salida");
            System.out.println(e.getMessage());
        }finally {
            cliente.close();
        }
    }

    // retorna si el dato fue guardado correctamente
    public void escribirDatosOutlier(DatoAlmacenado dato) {
        ObjectContainer cliente = server.openClient();
        try {
            cliente.store(dato);
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } finally {
            cliente.close();
        }
    }

    public List<DatoAlmacenado> leerDatosValidos() {
        DatoAlmacenado prototipo = new DatoAlmacenado(null, null, null, null, null, null);
        ObjectContainer cliente = server.openClient();
        ObjectSet<DatoAlmacenado> resultado;
        try {
            resultado = cliente.queryByExample(prototipo);
        } finally {
            cliente.close();
        }
        return resultado.subList(0, resultado.size() - 1);
    }

    public List<DatoAlmacenado> leerDatosValidos(DatoAlmacenado prototipo) {

        ObjectContainer cliente = server.openClient();
        ObjectSet<DatoAlmacenado> resultado;
        try {
            resultado = cliente.queryByExample(prototipo);
        } finally {
            cliente.close();
        }
        return resultado.subList(0, resultado.size() - 1);
    }
}
