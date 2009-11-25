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
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DatoAlmacenado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatos {

    public ObjectServer server;
    private ObjectContainer cliente;

    public SelectorDatos(ObjectServer serverBD) {
        server = serverBD;
    }

    private void abrirCliente() {
        cliente = server.openClient();
    }

    private void cerrarCliente() {
        cliente.close();
    }

    public void escribirDatos(Collection<DatoAlmacenado> datos) {
        abrirCliente();
        try {
            for (DatoAlmacenado datoAlmacenado : datos) {
                cliente.store(datoAlmacenado);
            }
            cliente.commit();
        } catch (DatabaseClosedException e) {
            System.out.println("la base que intenta ingresar se encuentra cerrada");
            System.out.println(e.getMessage());
        } catch (DatabaseReadOnlyException e) {
            System.out.println("la base que intenta ingresar esta en estado read-only");
            System.out.println(e.getMessage());
        } catch (Db4oIOException e) {
            System.out.println("Se produjo un error de entrada salida");
            System.out.println(e.getMessage());
        } finally {
            cerrarCliente();
        }
    }

    public List<DatoAlmacenado> seleccionar(Collection<Integer> idTRs, Collection<FactorClimatico> factores, Integer segundos) {
        Predicate<DatoAlmacenado> predicado = predicadoDatosTodos();
        if (idTRs != null) {
            predicado = predicadoDatosDeTR(idTRs);
        }
        if (factores != null) {
            predicado = conjuncion(predicado, predicadorDatosFactor(factores));
        }
        if (segundos != null) {
            Date hasta = Calendar.getInstance().getTime();
            Date desde = SelectorDatos.restarSegundos(hasta, segundos);
            predicado = conjuncion(predicado, predicadoDatosDesde(desde));
            predicado = conjuncion(predicado, predicadoDatosHasta(hasta));
        }

        return select(predicado);
    }

    public List<DatoAlmacenado> seleccionar(Collection<Integer> idTRs, Collection<FactorClimatico> factores, Date desde, Date hasta) {
        Predicate<DatoAlmacenado> predicado = predicadoDatosTodos();
        if (idTRs != null) {
            predicado = predicadoDatosDeTR(idTRs);
        }
        if (desde != null) {
            predicado = conjuncion(predicado, predicadoDatosDesde(desde));
        }
        if (hasta != null) {
            predicado = conjuncion(predicado, predicadoDatosHasta(hasta));
        }
        if (factores != null) {
            predicado = conjuncion(predicado, predicadorDatosFactor(factores));
        }
        return select(predicado);
    }

    private List<DatoAlmacenado> select(Predicate<DatoAlmacenado> predicado) {
        ObjectSet<DatoAlmacenado> resultado = null;
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

    public List<DatoAlmacenado> leerTodosLosDatos() {
        return select(predicadoDatosTodos());
    }

    public List<DatoAlmacenado> leerDatosDeTR(final Collection<Integer> trs) {
        return select(predicadoDatosDeTR(trs));
    }

    private Predicate<DatoAlmacenado> predicadoDatosTodos() {
        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return true;
            }
        };
    }

    private Predicate<DatoAlmacenado> predicadoDatosDeTR(final Collection<Integer> trs) {
        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return trs.contains(dato.getIdTR());
            }
        };
    }

    private Predicate<DatoAlmacenado> predicadoDatosDesde(final Date desde) {

        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return dato.getTimeStamp().after(desde);
            }
        };
    }

    private Predicate<DatoAlmacenado> predicadoDatosHasta(final Date hasta) {

        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return dato.getTimeStamp().before(hasta);
            }
        };
    }

    private Predicate<DatoAlmacenado> predicadorDatosFactor(final Collection<FactorClimatico> factores) {
        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return factores.contains(dato.getFactor());
            }
        };
    }

    public static Date restarSegundos(Date timeStamp, Integer seg) {
        Calendar timeStampRes = Calendar.getInstance();
        timeStampRes.setTime(timeStamp);
        timeStampRes.add(Calendar.SECOND, -seg);
        return timeStampRes.getTime();
    }

    private Predicate<DatoAlmacenado> conjuncion(final Predicate<DatoAlmacenado> p1, final Predicate<DatoAlmacenado> p2) {
        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return p1.match(dato) && p2.match(dato);
            }
        };
    }

    private Predicate<DatoAlmacenado> disyuncion(final Predicate<DatoAlmacenado> p1, final Predicate<DatoAlmacenado> p2) {
        return new Predicate<DatoAlmacenado>() {

            @Override
            public boolean match(DatoAlmacenado dato) {
                return p1.match(dato) || p2.match(dato);
            }
        };

    }

    public Map<Integer, List<DatoAlmacenado>> datosPorTR() {
        abrirCliente();
        Integer idTR;
        List<DatoAlmacenado> listaAuxiliar;
        Map<Integer, List<DatoAlmacenado>> resultado = new LinkedHashMap<Integer, List<DatoAlmacenado>>();

        Query query = cliente.query();
        query.descend("timeStamp").orderAscending();
        List<DatoAlmacenado> datoTotales = query.execute();
        for (DatoAlmacenado datoAlmacenado : datoTotales) {
            idTR = datoAlmacenado.getIdTR();
            if (resultado.containsKey(idTR)) {
                resultado.get(idTR).add(datoAlmacenado);
            } else {
                listaAuxiliar = new LinkedList<DatoAlmacenado>();
                listaAuxiliar.add(datoAlmacenado);
                resultado.put(idTR, listaAuxiliar);
            }
        }
        cerrarCliente();
        return resultado;
    }

    private void mostrarDatosAlmacenados(List<DatoAlmacenado> datos) {
        String extension = ".txt";
        String nombre = "DatosValidos";
        File filepath = new File(nombre + extension);
        filepath.delete();
        try {
            filepath.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SelectorDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.append(" ============ DATOS ALMACENADOS VALIDOS============ \n");
            for (DatoAlmacenado datoAlmacenado : datos) {
                salida.append(datoAlmacenado.toString() + "\n");
            }
            salida.close();
        } catch (java.io.IOException ioex) {
            System.out.println("se presentó el error: " + ioex.toString());
        }
    }
}
