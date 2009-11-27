/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictorTest;

import evaluador.Contador;
import evaluador.Particionador;
import evaluador.PredictorThread;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import predictor.Predictor;
import predictor.PredictorTodosConTodos;
import predictor.ResultadoRegla;
import static org.junit.Assert.*;

/**
 *
 * @author Santiago Avendaño
 */
public class ParticionadorTest {

    private static Collection<Predictor> predictores;
    private static Particionador particionador;
    private static final Integer tamanioParticion = 3;
    private static final Integer particionesEsperadas = 2;

    public ParticionadorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        particionador = new Particionador(tamanioParticion);
        predictores = new LinkedList<Predictor>();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        predictores.add(new PredictorTodosConTodos());
        predictores.add(new PredictorTodosConTodos());
        predictores.add(new PredictorTodosConTodos());
        predictores.add(new PredictorTodosConTodos());
        predictores.add(new PredictorTodosConTodos());
//        predictores.add(new PredictorTodosConTodos());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void cantidadDeParticiones() {
        Collection<Collection<PredictorThread>> particiones = particionador.particionar(predictores, new ConcurrentLinkedQueue<ResultadoRegla>());
        Integer particionesObtenidas = particiones.size();
        System.out.println("Particiones Obtenidas: " + particionesObtenidas.toString());
        System.out.println("Particiones Esperadas: " + particionesEsperadas);
        assertEquals(particionesEsperadas, particionesObtenidas);
    }
}
