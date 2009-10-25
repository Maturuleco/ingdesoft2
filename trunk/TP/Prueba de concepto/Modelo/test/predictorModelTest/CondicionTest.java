package predictorModelTest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import predictorModel.Comparador;
import predictorModel.Condicion;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
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
public class CondicionTest {

    private static Collection<Condicion> condiciones = null;
    private static Collection<DatoSensado> datosAEvaluar = null;

    public CondicionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        condiciones = new LinkedList<Condicion>();
        Condicion condicionIgual = new Condicion(FactorClimatico.temperatura, Comparador.igual, 10f);
        Condicion condicionMayor = new Condicion(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Condicion condicionMenor = new Condicion(FactorClimatico.temperatura, Comparador.menor, 20f);
        Condicion condicionMenorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Condicion condicionMenorIgual11 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Condicion condicionMayorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Condicion condicionMayorIgual9 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        condiciones.add(condicionIgual);
        condiciones.add(condicionMayor);
        condiciones.add(condicionMenor);
        condiciones.add(condicionMenorIgual10);
        condiciones.add(condicionMenorIgual11);
        condiciones.add(condicionMayorIgual10);
        condiciones.add(condicionMayorIgual9);
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void aplicarcondicionsTemperaturaTest() {
        Condicion condicionIgual = new Condicion(FactorClimatico.temperatura, Comparador.igual, 10f);
        Condicion condicionMayor = new Condicion(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Condicion condicionMenor = new Condicion(FactorClimatico.temperatura, Comparador.menor, 20f);
        Condicion condicionMenorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Condicion condicionMenorIgual11 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Condicion condicionMayorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Condicion condicionMayorIgual9 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        Date ahora = Calendar.getInstance().getTime();
        DatoSensado dato1 = new DatoSensado(1, ahora, FactorClimatico.temperatura, 10f);

        assertTrue("La temperatura deberia ser igual 10f", condicionIgual.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor que 5f", condicionMayor.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor que 20f", condicionMenor.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor o igual 10f", condicionMenorIgual10.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor o igual 11f", condicionMenorIgual11.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor o igual 10f", condicionMayorIgual10.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor o igual 9f", condicionMayorIgual9.aplicar(dato1));

    }

    @Test
    public void aplicarcondicionsNoTipoTest() {
        Condicion condicionIgual = new Condicion(FactorClimatico.temperatura, Comparador.igual, 10f);
        Condicion condicionMayor = new Condicion(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Condicion condicionMenor = new Condicion(FactorClimatico.temperatura, Comparador.menor, 20f);
        Condicion condicionMenorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Condicion condicionMenorIgual11 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Condicion condicionMayorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Condicion condicionMayorIgual9 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        Date ahora = Calendar.getInstance().getTime();
        DatoSensado dato1 = new DatoSensado(1, ahora, FactorClimatico.direccion_viento, 10f);

        assertTrue("La temperatura no deberia compararse", condicionIgual.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMayor.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMenor.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMenorIgual10.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMenorIgual11.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMayorIgual10.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", condicionMayorIgual9.aplicar(dato1));

    }

    @Test
    public void escribirCondicionesTest() {
        Condicion.writeCondiciones(condiciones, "condiciones.rule");
    }

    @Test
    public void leerCondicionesTest() {
        Collection<Condicion> condicionesCargadas = new LinkedList<Condicion>();
        try {
            FileReader fr = new FileReader("condiciones.rule");
            condicionesCargadas = Condicion.readCondiciones(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }

        assertTrue(condiciones.size() == condicionesCargadas.size());
    }
}
