/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selectorDatos;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.DatoAlmacenado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatos {

    public ObjectServer server;

    public SelectorDatos(ObjectServer serverBD) {
        server = serverBD;
    }

    public void escribirDatos(Collection<DatoAlmacenado> datos) {
        ObjectContainer cliente = server.openClient();
        try {
            for (DatoAlmacenado datoAlmacenado : datos) {
                cliente.store(datoAlmacenado);
            }
            cliente.commit();
        } catch(DatabaseClosedException e){
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch(DatabaseReadOnlyException e){
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } catch (Db4oIOException e) {
            System.out.println("Se produjo un error de entrada salida");
            System.out.println(e.getMessage());
        }finally {
            cliente.close();
        }

    }

    public Map<FactorClimatico, Collection<DatoAlmacenado>> leerTodosLosDatos() {
        DatoAlmacenado prototipo = new DatoAlmacenado(null, null, null,null, null, null);
        ObjectSet<DatoAlmacenado> resultado = null;
        ObjectContainer cliente = server.openClient();
        try {
            resultado = cliente.queryByExample(prototipo);
        } catch(DatabaseClosedException e){
            System.out.println("la base de datos se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (Db4oIOException e){
            System.out.println("Error de I/O");
            System.out.println(e.getMessage());
        } finally {
            cliente.close();
        }
        return ordenarPorFactor(resultado);
    }

    public static Map<FactorClimatico, Collection<DatoAlmacenado>> ordenarPorFactor(Collection<DatoAlmacenado> datos) {
        Map<FactorClimatico, Collection<DatoAlmacenado>> result = new EnumMap<FactorClimatico, Collection<DatoAlmacenado>>(FactorClimatico.class);

        for (FactorClimatico factor : FactorClimatico.values()) {
            result.put(factor, new LinkedList<DatoAlmacenado>());
        }

        for (DatoAlmacenado datoAlmacenado : datos ) {
            result.get(datoAlmacenado.getFactor()).add(datoAlmacenado);
        }

        return result;
    }
}
