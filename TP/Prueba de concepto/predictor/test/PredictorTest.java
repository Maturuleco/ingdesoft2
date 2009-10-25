
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import model.DatoSensado;
import model.FactorClimatico;
import predictor.Predictor;
import org.junit.*;
import predictor.PredictorHuracan;
import static org.junit.Assert.*;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class PredictorTest {

    private Predictor predictor;
    private Map<Integer, Collection<DatoSensado>> datosPorTR;

    @Before
    public void setUp() {
        Collection<DatoSensado> datos = new LinkedList<DatoSensado>();
        Date timeStamp = Calendar.getInstance().getTime();
        DatoSensado datoHumedad = new DatoSensado(1, timeStamp, FactorClimatico.humedad, 95f);
        DatoSensado datoTemperatura = new DatoSensado(2, timeStamp, FactorClimatico.temperatura, 30f);
        DatoSensado datoVelocidadViento = new DatoSensado(3, timeStamp, FactorClimatico.velocidad_viento, 70f);
        datos.add(datoHumedad);
        datos.add(datoTemperatura);
        datos.add(datoVelocidadViento);
        datosPorTR = new HashMap<Integer, Collection<DatoSensado>>();
        datosPorTR.put(1, datos);
        
    }

    @Test
    public void detectarHuracanesTest() {
        predictor = new PredictorHuracan();
        assertFalse(predictor.analizar(datosPorTR).isEmpty());
    }
}
