package predictorTest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import model.DataSource;
import model.DatoAlmacenado;
import model.FactorClimatico;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import predictor.Predictor;
import modelo.Comparador;
import modelo.Condicion;
import modelo.Regla;
import predictor.PredictorPorTipo;
import predictor.PredictorTodosConTodos;
import selectorDatos.SelectorDatos;

/**
 *
 * @author Santiago Avendaño
 */
public class PredictorTest {

    Predictor predictor;
    Regla regla;
    Collection<DatoAlmacenado> datos;
    Map<FactorClimatico,Collection<DatoAlmacenado>> datosOrdenadosPorFactor;

    public PredictorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        Collection<Condicion> condiciones = new LinkedList<Condicion>();
        Condicion condicionIgual = new Condicion(FactorClimatico.temperatura, Comparador.igual, 10f);
        Condicion condicionMayor = new Condicion(FactorClimatico.temperatura, Comparador.mayor, 5f);
        Condicion condicionMenor = new Condicion(FactorClimatico.temperatura, Comparador.menor, 20f);
        Condicion condicionMenorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 10f);
        Condicion condicionMenorIgual11 = new Condicion(FactorClimatico.temperatura, Comparador.meneq, 11f);
        Condicion condicionMayorIgual10 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 10f);
        Condicion condicionMayorIgual9 = new Condicion(FactorClimatico.temperatura, Comparador.mayeq, 9f);

        condiciones.add(condicionIgual);
        condiciones.add(condicionMayor);
        condiciones.add(condicionMenor);
        condiciones.add(condicionMenorIgual10);
        condiciones.add(condicionMenorIgual11);
        condiciones.add(condicionMayorIgual10);
        condiciones.add(condicionMayorIgual9);
        regla = new Regla(condiciones, "Se detectó un huracan");
    }

    @After
    public void tearDown() {
    }

    /*
     * Hay una condicion sobre la temperatura y el dato sobre temperatura la cumple
     */
    @Test
    public void detectarAlertaTest() {
        DatoAlmacenado dato1 = new DatoAlmacenado(1, Calendar.getInstance().getTime(), FactorClimatico.temperatura, 10.0f,1,DataSource.terminal_remota);
        datos = new LinkedList<DatoAlmacenado>();
        datos.add(dato1);
        datosOrdenadosPorFactor = SelectorDatos.ordenarPorFactor(datos);
        predictor = new PredictorPorTipo(regla,datosOrdenadosPorFactor, "lugar" );
        assertTrue(predictor.analizar());
        predictor = new PredictorTodosConTodos(regla, datos, "lugar");
        assertTrue(predictor.analizar());
    }

    /*
     * Hay una regla sobre la temperatura, pero el dato sobre temperatura no la cumple
     */
    @Test
    public void noDetectarAlertaTest() {
        DatoAlmacenado dato1 = new DatoAlmacenado(1, Calendar.getInstance().getTime(), FactorClimatico.temperatura, 5.0f,1, DataSource.terminal_remota);
        datos = new LinkedList<DatoAlmacenado>();
        datos.add(dato1);
        datosOrdenadosPorFactor = SelectorDatos.ordenarPorFactor(datos);
        predictor = new PredictorPorTipo(regla,datosOrdenadosPorFactor, "lugar" );
        assertFalse(predictor.analizar());
        predictor = new PredictorTodosConTodos(regla, datos, "lugar");
        assertFalse(predictor.analizar());
    }

    /*
     * Hay una regla sobre la temperatura y ningun dato de temperatura
     */
    @Test
    public void noDetectarAlertaTest2() {
        DatoAlmacenado dato1 = new DatoAlmacenado(1, Calendar.getInstance().getTime(), FactorClimatico.humedad, 10.0f,1,DataSource.terminal_remota);
        datos = new LinkedList<DatoAlmacenado>();
        datos.add(dato1);
        datosOrdenadosPorFactor = SelectorDatos.ordenarPorFactor(datos);
        predictor = new PredictorPorTipo(regla,datosOrdenadosPorFactor , "lugar");
        assertFalse(predictor.analizar());
        predictor = new PredictorTodosConTodos(regla, datos, "lugar");
        assertFalse(predictor.analizar());
    }
}
