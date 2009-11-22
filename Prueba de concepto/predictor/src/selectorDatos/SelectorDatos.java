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

    public List<DatoAlmacenado> leerTodosLosDatos() {
        DatoAlmacenado prototipo = new DatoAlmacenado(null, null, null, null, null, null);
        ObjectSet<DatoAlmacenado> resultado = null;
        abrirCliente();
        try {
            resultado = cliente.queryByExample(prototipo);
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

    // TODO: implementar leerDatosDeTR (Collection<Integer> trs)
    public List<DatoAlmacenado> leerDatosDeTR(Integer idTR) {
        DatoAlmacenado prototipo = new DatoAlmacenado(null, null, null, null, idTR, null);
        ObjectSet<DatoAlmacenado> resultado = null;
        abrirCliente();
        try {
            resultado = cliente.queryByExample(prototipo);
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

    public List<DatoAlmacenado> leerUltimosDatosCantidad(Integer cantidad) {
        abrirCliente();
        List<DatoAlmacenado> resultado;
        Query query = cliente.query();
        query.constrain(DatoAlmacenado.class);
        query.descend("timeStamp").orderDescending();
        resultado = query.execute();
        cerrarCliente();
        if (resultado.size() >= cantidad) {
            return resultado.subList(0, cantidad);
        } else {
            return resultado.subList(0, resultado.size());
        }
    }

    public List<DatoAlmacenado> leerUltimosDatosTiempo(int seg) {
       
        abrirCliente();
        List<DatoAlmacenado> resultado;
        Query query = cliente.query();
        query.constrain(DatoAlmacenado.class);
        query.descend("timeStamp").orderDescending();
        resultado = query.execute();
        cerrarCliente();

        Date limiteInferior = restarTiempo(seg);
        int hasta = 0;
        for (DatoAlmacenado datoAlmacenado : resultado) {
            if (datoAlmacenado.getTimeStamp().before(limiteInferior)){
                break;
            } else {
                hasta++;
            }
        }
        return resultado.subList(0, hasta);
    }

    private Date restarTiempo(Integer seg){
        Calendar timestamp = Calendar.getInstance();
        timestamp.add(Calendar.SECOND,-seg);
        Date timestampDesde = timestamp.getTime();
        return timestampDesde;
    }

    public Map<Integer, List<DatoAlmacenado>> datosPorTR() {
        abrirCliente();
        Integer idTR;
        List<DatoAlmacenado> listaAuxiliar;
        Map<Integer, List<DatoAlmacenado>> resultado = new LinkedHashMap<Integer, List<DatoAlmacenado>>();

        Query query = cliente.query();
        query.constrain(DatoAlmacenado.class);
        query.descend("timeStamp").orderAscending();
        List<DatoAlmacenado> datoTotales = query.execute();
        mostrarDatosAlmacenados(datoTotales);
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
