package reglas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import predictorModel.Comparador;
import predictorModel.Regla;
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
public class ReglaTest {

    private static Collection<Regla> reglas = null;
    private static Collection<DatoSensado> datosAEvaluar = null;

    public ReglaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        reglas = new LinkedList<Regla>();
        Regla reglaIgual = new Regla(FactorClimatico.temperatura, Comparador.igual, 10f);
        Regla reglaMayor = new Regla(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Regla reglaMenor = new Regla(FactorClimatico.temperatura, Comparador.menor, 20f);
        Regla reglaMenorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Regla reglaMenorIgual11 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Regla reglaMayorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Regla reglaMayorIgual9 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        reglas.add(reglaIgual);
        reglas.add(reglaMayor);
        reglas.add(reglaMenor);
        reglas.add(reglaMayorIgual10);
        reglas.add(reglaMenorIgual11);
        reglas.add(reglaMayorIgual10);
        reglas.add(reglaMayorIgual9);
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
    public void aplicarReglasTemperaturaTest() {
        Regla reglaIgual = new Regla(FactorClimatico.temperatura, Comparador.igual, 10f);
        Regla reglaMayor = new Regla(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Regla reglaMenor = new Regla(FactorClimatico.temperatura, Comparador.menor, 20f);
        Regla reglaMenorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Regla reglaMenorIgual11 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Regla reglaMayorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Regla reglaMayorIgual9 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        Date ahora = Calendar.getInstance().getTime();
        DatoSensado dato1 = new DatoSensado(1, ahora, FactorClimatico.temperatura, 10f);

        assertTrue("La temperatura deberia ser igual 10f", reglaIgual.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor que 5f", reglaMayor.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor que 20f", reglaMenor.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor o igual 10f", reglaMenorIgual10.aplicar(dato1));
        assertTrue("La temperatura deberia ser menor o igual 11f", reglaMenorIgual11.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor o igual 10f", reglaMayorIgual10.aplicar(dato1));
        assertTrue("La temperatura deberia ser mayor o igual 9f", reglaMayorIgual9.aplicar(dato1));

    }

    @Test
    public void aplicarReglasNoTipoTest() {
        Regla reglaIgual = new Regla(FactorClimatico.temperatura, Comparador.igual, 10f);
        Regla reglaMayor = new Regla(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Regla reglaMenor = new Regla(FactorClimatico.temperatura, Comparador.menor, 20f);
        Regla reglaMenorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Regla reglaMenorIgual11 = new Regla(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Regla reglaMayorIgual10 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Regla reglaMayorIgual9 = new Regla(FactorClimatico.temperatura, Comparador.mayeq, 10f);

        Date ahora = Calendar.getInstance().getTime();
        DatoSensado dato1 = new DatoSensado(1, ahora, FactorClimatico.direccion_viento, 10f);

        assertTrue("La temperatura no deberia compararse", reglaIgual.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMayor.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMenor.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMenorIgual10.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMenorIgual11.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMayorIgual10.aplicar(dato1));
        assertTrue("La temperatura no deberia compararse", reglaMayorIgual9.aplicar(dato1));

    }

    @Test
    @Ignore
    public void escribirReglasTest() {
        Regla.writeReglas(reglas, "Reglas.rule");
    }

    @Test
    public void leerReglasTest() {
        Collection<Regla> reglasCargadas = new LinkedList<Regla>();
        try {
            FileReader fr = new FileReader("Reglas.rule");
            reglasCargadas = Regla.readReglas(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }

        assertTrue(reglas.size() == reglasCargadas.size());
    }
}
