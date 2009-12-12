/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package selectorResultados;

import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import evaluador.ResultadoEvaluacion;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Santiago Avendaño
 */
public class ResultadosDAO {

    private ObjectServer server;
    private ObjectContainer cliente;

    public ResultadosDAO() {
    }

    public ResultadosDAO(ObjectServer serverBD) {
        this.server = serverBD;
    }

    private void abrirCliente() {
        cliente = server.openClient();
    }

    private void cerrarCliente() {
        cliente.close();
    }

    public void setServerResultados(ObjectServer serverResultados) {
        this.server = serverResultados;
    }

//    public void escribirResultados(Collection<ResultadoEvaluacion> resultados) {
//        abrirCliente();
//        try {
//            for (ResultadoEvaluacion resultado : resultados) {
//                cliente.store(resultado);
//            }
//            cliente.commit();
//        } catch (DatabaseClosedException e) {
//            System.out.println("la base que intenta ingresar se encuentra cerrada");
//            System.out.println(e.getMessage());
//        } catch (DatabaseReadOnlyException e) {
//            System.out.println("la base que intenta ingresar esta en estado read-only");
//            System.out.println(e.getMessage());
//        } finally {
//            cerrarCliente();
//        }
//    }

    public List<ResultadoEvaluacion> seleccionar(Collection<Integer> idTRs, Collection<Integer> idModelos, Integer segundos) {
        Predicate<ResultadoEvaluacion> predicado = predicadoDatosTodos();
        if (idTRs != null) {
            predicado = predicadoDatosDeTR(idTRs);
        }
        if (idModelos != null) {
            predicado = conjuncion(predicado, predicadoDatosDeModelo(idModelos));
        }
        if (segundos != null) {
            Date hasta = Calendar.getInstance().getTime();
            Date desde = restarSegundos(hasta, segundos);
            predicado = conjuncion(predicado, predicadoDatosDesde(desde));
        }

        return select(predicado);
    }

    private List<ResultadoEvaluacion> select(Predicate<ResultadoEvaluacion> predicado) {
        ObjectSet<ResultadoEvaluacion> resultado = null;
        abrirCliente();

        try {
            resultado = cliente.query(predicado);
        } catch (DatabaseClosedException e) {
            System.out.println("la base de datos se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (Db4oIOException e) {
            System.out.println("Error de I/O");
            System.out.println(e.getMessage());
        } finally {
            cerrarCliente();
        }
        return resultado;
    }

    private Predicate<ResultadoEvaluacion> predicadoDatosTodos() {
        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return true;
            }
        };
    }

    private Predicate<ResultadoEvaluacion> predicadoDatosDeTR(final Collection<Integer> trs) {
        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return trs.contains(dato.getIdTR());
            }
        };
    }

    private Predicate<ResultadoEvaluacion> predicadoDatosDeModelo(final Collection<Integer> modelos) {
        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return modelos.contains(dato.getIdModelo());
            }
        };
    }

    private Predicate<ResultadoEvaluacion> predicadoDatosDesde(final Date desde) {

        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return dato.getTimeStamp().after(desde);
            }
        };
    }

    private Date restarSegundos(Date timeStamp, Integer seg) {
        Calendar timeStampRes = Calendar.getInstance();
        timeStampRes.setTime(timeStamp);
        timeStampRes.add(Calendar.SECOND, -seg);
        return timeStampRes.getTime();
    }

        private Predicate<ResultadoEvaluacion> conjuncion(final Predicate<ResultadoEvaluacion> p1, final Predicate<ResultadoEvaluacion> p2) {
        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return p1.match(dato) && p2.match(dato);
            }
        };
    }

    private Predicate<ResultadoEvaluacion> disyuncion(final Predicate<ResultadoEvaluacion> p1, final Predicate<ResultadoEvaluacion> p2) {
        return new Predicate<ResultadoEvaluacion>() {

            @Override
            public boolean match(ResultadoEvaluacion dato) {
                return p1.match(dato) || p2.match(dato);
            }
        };

    }

}
