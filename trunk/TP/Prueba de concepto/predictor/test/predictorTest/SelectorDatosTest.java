/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorTest;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
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
//        DatoSensado proto = new DatoSensado(null, null, FactorClimatico.temperatura, null);
//        ObjectSet result=db.queryByExample(proto);
//        System.out.println(result.size());
//        while(result.hasNext()) {
//            System.out.println(result.next());
//        }
        List<DatoSensado> datos = db.query(DatoSensado.class);
        for (DatoSensado datoSensado : datos) {
            System.out.println(datoSensado.toString());
        }
        //assertFalse(predictor.analizar(regla, datos));
    }

    @Test
    public void escribirDatosAlmacenadosTest() {
        String rutabd = "resources/dbejemploDA.yap";
        DatoAlmacenado dato1;
        new File(rutabd).delete();
        ObjectContainer db = Db4o.openFile(rutabd);

        for (int i = 0; i < 10000; i++) {
            dato1 = new DatoAlmacenado(i, Calendar.getInstance().getTime(), FactorClimatico.presion, 5.0f, 3, DataSource.triangulador);
            db.store(dato1);
        }

        List<DatoAlmacenado> datos = db.query(DatoAlmacenado.class);
        assertTrue(datos.size() == 10000);
        db.close();
    }
}
