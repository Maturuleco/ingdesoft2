/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorTest;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import model.DataSource;
import model.DatoAlmacenado;
import model.DatoSensado;
import model.FactorClimatico;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Santiago Avendaño
 */
public class SelectorDatosTest {

    public SelectorDatosTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void escribirDatosTest() {
        DatoSensado dato1 = new DatoSensado(1, Calendar.getInstance().getTime(), FactorClimatico.temperatura, 5.0f);
        ObjectContainer db = Db4o.openFile("resources/dbejemplo.yap");
        db.store(dato1);
        List<DatoSensado> datos = db.query(DatoSensado.class);
        for (DatoSensado datoSensado : datos) {
            System.out.println(datoSensado.toString());
        }
    }

    @Test
    public void escribirDatosAlmacenadosTest() {
        String rutabd = "resources/dbejemploDA.yap";
        DatoAlmacenado dato1;
        new File(rutabd).delete();
        ObjectServer server1 = Db4o.openServer(rutabd, 0);
        ObjectContainer cliente1 = server1.openClient();
        ObjectContainer cliente2 = server1.openClient();
        for (int i = 0; i < 10000; i++) {
            dato1 = new DatoAlmacenado(i, Calendar.getInstance().getTime(), FactorClimatico.presion, 5.0f, 3, DataSource.terminal_remota);
            cliente1.store(dato1);
            dato1 = new DatoAlmacenado(i, Calendar.getInstance().getTime(), FactorClimatico.temperatura, 5.0f, 3, DataSource.terminal_remota);
            cliente2.store(dato1);
        }

        List<DatoAlmacenado> datos = cliente1.query(DatoAlmacenado.class);
        assertTrue(datos.size() == 10000);
        datos = cliente2.query(DatoAlmacenado.class);
        assertTrue(datos.size() == 10000);
        cliente1.commit();
        cliente2.commit();
        datos = cliente2.query(DatoAlmacenado.class);
        assertTrue(datos.size() == 20000);

        cliente1.close();
        cliente2.close();
    }
}
