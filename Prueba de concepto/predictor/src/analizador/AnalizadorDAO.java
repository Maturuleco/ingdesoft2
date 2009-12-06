/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizador;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import java.util.List;
import Datos.DatoAlmacenado;

/**
 *
 * @author Santiago Avendaño
 */
public class AnalizadorDAO {
    private ObjectServer serverResultados;

    public AnalizadorDAO() {
    }

    public void setServerResultados(ObjectServer serverResultados) {
        this.serverResultados = serverResultados;
    }

    public void escribirResultados(Collection<ResultadoEvaluacion> resultados) {
        ObjectContainer cliente = serverResultados.openClient();
        try {
            for (ResultadoEvaluacion resultado : resultados) {
                cliente.store(resultado);
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

    public List<ResultadoEvaluacion> leerResultados() {
        ResultadoEvaluacion prototipo = new ResultadoEvaluacion(null, null, null, null);
        ObjectContainer cliente = serverResultados.openClient();
        ObjectSet<ResultadoEvaluacion> resultado;
        try {
            resultado = cliente.queryByExample(prototipo);
        } finally {
            cliente.close();
        }
        for (ResultadoEvaluacion resultadoEvaluacion : resultado) {
            System.out.println(resultadoEvaluacion.toString());
        }
        return resultado;
    }
}
