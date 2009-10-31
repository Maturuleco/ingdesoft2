/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorTest;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.DataSource;
import model.DatoAlmacenado;
import model.FactorClimatico;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import selectorDatos.SelectorDatos;
import static org.junit.Assert.*;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatosTest {
    private static String directorioBDs = "../bases";
    private static String rutabd = directorioBDs+"/dbTestSelector.yap";
    private static ObjectServer server;
    private static SelectorDatos selector;
    private static Collection<DatoAlmacenado> datosGenerados;
    private ObjectContainer cliente1;

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

    public static void inicializarSelector(){
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

    public static Collection<DatoAlmacenado> generarDatos(){
        Collection<DatoAlmacenado> datos = new LinkedList<DatoAlmacenado>();
        Date timeStamp;
        DatoAlmacenado dato = null;
        for (int idTR = 1; idTR <= 10; idTR++) {
            for(float valor = 0.0f; valor < 100.0f; valor+=10.0f){
                for (FactorClimatico factor : FactorClimatico.values()) {
                    timeStamp = Calendar.getInstance().getTime();
                    switch(factor){
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
                }
            }
        }
        return datos;
    }



    @After
    public void tearDown() {

    }

    @Test
    public void escribirDatosAlmacenadosTest() {
        
        cliente1 = server.openClient();
        List<DatoAlmacenado> datos = cliente1.query(DatoAlmacenado.class);
        assertTrue(datos.size() == 600);
        cliente1.commit();
        assertTrue(datos.size() == 600);
        cliente1.close();
    }

    @Test
    public void seleccionarPorTR(){
        cliente1 = server.openClient();
        Collection<DatoAlmacenado> datosTR = selector.leerDatosDeTR(1);
        System.out.println(datosTR.size());
        for (DatoAlmacenado datoAlmacenado : datosTR) {
            System.out.println(datoAlmacenado.mostrar());
        }
        assertTrue(datosTR.size() == 60);
        cliente1.commit();
        assertTrue(datosTR.size() == 60);
        cliente1.close();
    }
}
