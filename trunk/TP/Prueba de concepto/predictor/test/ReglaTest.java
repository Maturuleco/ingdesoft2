/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Santiago Avendaño
 */
public class ReglaTest {

    private static Regla regla = null;
    private static Collection<DatoSensado> datosAEvaluar = null;

    public ReglaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        datosAEvaluar = new LinkedList<DatoSensado>();
        Date ahora = Calendar.getInstance().getTime();
        DatoSensado dato1 = new DatoSensado(1, ahora, FactorClimatico.temperatura, 30f);
        DatoSensado dato2 = new DatoSensado(2, ahora, FactorClimatico.humedad, 90f);
        DatoSensado dato3 = new DatoSensado(1, ahora, FactorClimatico.velocidad_viento, 70f);
        datosAEvaluar.add(dato1);
        datosAEvaluar.add(dato2);
        datosAEvaluar.add(dato3);
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
        Regla reglaIgual = new Regla(Comparador.igual, 10f, FactorClimatico.temperatura);
        Regla reglaMayor = new Regla(Comparador.mayor, 5f, FactorClimatico.temperatura);
        Regla reglaMenor = new Regla(Comparador.menor, 20f, FactorClimatico.temperatura);
        Regla reglaMenorIgual10 = new Regla(Comparador.meneq, 10f, FactorClimatico.temperatura);
        Regla reglaMenorIgual11 = new Regla(Comparador.meneq, 11f, FactorClimatico.temperatura);
        Regla reglaMayorIgual10 = new Regla(Comparador.mayeq, 10f, FactorClimatico.temperatura);
        Regla reglaMayorIgual9 = new Regla(Comparador.mayeq, 10f, FactorClimatico.temperatura);
                
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
        Regla reglaIgual = new Regla(Comparador.igual, 10f, FactorClimatico.temperatura);
        Regla reglaMayor = new Regla(Comparador.mayor, 5f, FactorClimatico.temperatura);
        Regla reglaMenor = new Regla(Comparador.menor, 20f, FactorClimatico.temperatura);
        Regla reglaMenorIgual10 = new Regla(Comparador.meneq, 10f, FactorClimatico.temperatura);
        Regla reglaMenorIgual11 = new Regla(Comparador.meneq, 11f, FactorClimatico.temperatura);
        Regla reglaMayorIgual10 = new Regla(Comparador.mayeq, 10f, FactorClimatico.temperatura);
        Regla reglaMayorIgual9 = new Regla(Comparador.mayeq, 10f, FactorClimatico.temperatura);

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

}