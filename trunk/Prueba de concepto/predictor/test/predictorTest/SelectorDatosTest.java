/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorTest;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.query.Query;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DataSource;
import model.DatoAlmacenado;
import model.FactorClimatico;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import selectorDatos.SelectorDatos;
import static org.junit.Assert.*;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatosTest {

    private static String directorioBDs = "../bases";
    private static String rutabd = directorioBDs + "/dbTestSelector.yap";
    private static ObjectServer server;
    private static SelectorDatos selector;
    private static Collection<DatoAlmacenado> datosGenerados;
    private ObjectContainer cliente1;
    private static Integer cantidadDatosEnBase;

    public SelectorDatosTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        inicializarSelector();
        datosGenerados = generarDatos();
        selector.escribirDatos(datosGenerados);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        server.close();
    }

    @Before
    public void setUp() {
    }

    public static void inicializarSelector() {
        new File(directorioBDs).mkdir();
        File serverPath = new File(rutabd);
        serverPath.delete();
        try {
            serverPath.createNewFile();
        } catch (IOException ex) {
            System.out.println("No se pudo crear el archivo");
        }
        server = Db4o.openServer(serverPath.getAbsolutePath(), 0);
        System.out.println("Se creo la base de prueba en la ruta:" + serverPath.getAbsolutePath());
        selector = new SelectorDatos(server);
    }

    public static Collection<DatoAlmacenado> generarDatos() {
        Collection<DatoAlmacenado> datos = new LinkedList<DatoAlmacenado>();
        Date timeStamp;
        DatoAlmacenado dato = null;
        cantidadDatosEnBase = 0;
        for (int idTR = 1; idTR <= 10; idTR++) {
            for (float valor = 0.0f; valor < 100.0f; valor += 10.0f) {
                for (FactorClimatico factor : FactorClimatico.values()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SelectorDatosTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    timeStamp = Calendar.getInstance().getTime();

                    switch (factor) {
                        case direccion_viento:
                            dato = new DatoAlmacenado(1, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                        case humedad:
                            dato = new DatoAlmacenado(2, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                        case lluvias:
                            dato = new DatoAlmacenado(3, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                        case presion:
                            dato = new DatoAlmacenado(4, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                        case temperatura:
                            dato = new DatoAlmacenado(5, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                        case velocidad_viento:
                            dato = new DatoAlmacenado(6, timeStamp, factor, valor, idTR, DataSource.terminal_remota);
                            break;
                    }
                    datos.add(dato);
                    cantidadDatosEnBase++;
                }
            }
        }
        System.out.println("Se generaron: " + cantidadDatosEnBase + " datos");
        return datos;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void seleccionarTodos() {
        List<DatoAlmacenado> datos = selector.seleccionar(null, null, null, null);
        assertTrue(datos.size() == cantidadDatosEnBase);
        System.out.println("Se recolectaron " + cantidadDatosEnBase + "datos de la BD");
    }

    @Test
    public void seleccionarPorTRs() {
        Collection<Integer> trs = new TreeSet<Integer>();
        trs.add(1);
        trs.add(2);
        Collection<DatoAlmacenado> datosTR = selector.seleccionar(trs, null, null, null);
        assertTrue(datosTR.size() == 120);
    }

    @Test
    public void seleccionarPorFactor() {
        Collection<FactorClimatico> factores = new TreeSet<FactorClimatico>();
        factores.add(FactorClimatico.humedad);
        factores.add(FactorClimatico.lluvias);
        Collection<DatoAlmacenado> datosFactor = selector.seleccionar(null, factores, null, null);
        assertTrue(datosFactor.size() == 200);
    }

    @Test
    public void seleccionarPorTRsYFactor() {
        Collection<Integer> trs = new TreeSet<Integer>();
        trs.add(1);
        trs.add(2);
        Collection<FactorClimatico> factores = new TreeSet<FactorClimatico>();
        factores.add(FactorClimatico.humedad);
        factores.add(FactorClimatico.lluvias);
        Collection<DatoAlmacenado> datosTRFactor = selector.seleccionar(trs, factores, null, null);
        assertTrue(datosTRFactor.size() == 40);
    }

    @Test
    public void seleccionarUltimosTiempo() {
        //System.out.println("======Ultimos por tiempo ======");
        Date ahora = Calendar.getInstance().getTime();
        Date desde = SelectorDatos.restarSegundos(ahora, 5);
        List<DatoAlmacenado> datosOrdenados = selector.seleccionar(null, null, desde, ahora);
        //System.out.println("TimeStamp Desde: " + desde.getTime());
        //System.out.println("TimeStamp Hasta: " + ahora.getTime());
        for (DatoAlmacenado datoAlmacenado : datosOrdenados) {
            Long timeStamp = datoAlmacenado.getTimeStamp().getTime();
            assertTrue("El dato debe ser posterior a " + desde, timeStamp >= desde.getTime());
            assertTrue("El dato debe ser posterior a " + ahora, timeStamp <= ahora.getTime());
            //System.out.println(timeStamp);
        }

    }

    @Test
    public void seleccionarDatosPorTR() {
        Map<Integer, List<DatoAlmacenado>> datosPorTR = selector.datosPorTR();
        List<DatoAlmacenado> datosDeTR;
        for (Integer idTR : datosPorTR.keySet()) {
            datosDeTR = datosPorTR.get(idTR);
            assert (datosDeTR.size() == 60);
        }

    }
}
