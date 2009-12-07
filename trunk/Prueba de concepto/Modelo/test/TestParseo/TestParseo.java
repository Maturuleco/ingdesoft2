package TestParseo;

 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.ParseException;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import Datos.DataSource;
import Datos.DatoSensado;
import Datos.FactorClimatico;
import ModeloTerminal.CoordenadaGlobal;
import ModeloTerminal.ModeloTerminalRemota;
import ModeloTerminal.PuntoCardinal;
import ModeloTerminal.UbicacionGeografica;
import model.Mensaje;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author matiaz
 */
public class TestParseo {

    private Mensaje msj;
    private String msjString;

    public TestParseo() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        msj = new Mensaje(new Integer(32), DataSource.estacion_central);
        Date ahora;
        DatoSensado ds;
        long timeToWait = 1000;
        for(int i = 1 ; i < 12 ; i++ ) {
            ahora = new Date();
            ds = new DatoSensado(new Integer(i), ahora, FactorClimatico.presion, new Float(i + 0.65) );
            msj.addDato(ds);
       /*
            try {
                wait(timeToWait);
            } catch (InterruptedException ex) {
                System.out.println("no quizo esperar el wait del SetUp");
            }
       */
        }
        msjString = msj.toString();

    }

    @After
    public void tearDown() {
    }



    @Test
    public void testConversionString() {
        System.out.println("\nPrint del msj convertido a String:\n");
        System.out.println(msjString);
        System.out.println("\nFin del Print del msj convertido a String:\n");
    }

    @Test
    public void testEqualsMensaje() {
        assert(msj.equals(msj));
    }

    @Test
    public void parseDate() {
        Date date = new Date();
        String dateString = String.valueOf(date.getTime());
        Date date2;
        date2 = new Date(Long.valueOf(dateString));
        assertEquals(date.getTime(), date2.getTime());
    }

    @Test
    public void splitConPipes() {
        System.out.println("\nTest de Conversion DESDE String:\n");
        String[] partes = msjString.split("\\|");
        System.out.println(msjString+"\n");
        System.out.println("primer elemento: "+partes[0]+"\n");
        System.out.println("segundo elemento: "+partes[1]+"\n");
        System.out.println("tercer elemento: "+partes[2]+"\n");
        System.out.println("ultimo elemento: "+partes[(partes.length-1)]+"\n");
    }

    @Test
    public void testConversionDesdeString() {
        Mensaje msjLevantado;
        try {
            msjLevantado = Mensaje.parse(msjString);
            assertEquals(msj.toString(), msjLevantado.toString());
            assertEquals(msj, msjLevantado);

        } catch (ParseException ex) {
            System.out.println("\nError de Perseo\n");
            Logger.getLogger(TestParseo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Test
    public void testParseUbicacionGeográfica() {
        try {
            CoordenadaGlobal latitud = new CoordenadaGlobal(PuntoCardinal.Este, 54);
            CoordenadaGlobal longitud = new CoordenadaGlobal(PuntoCardinal.Sur, 32);
            UbicacionGeografica t1 = new UbicacionGeografica(latitud, longitud);
            String str = t1.toString();
            System.out.println("A String: " + str + "\n");
            UbicacionGeografica t2 = UbicacionGeografica.parse(str);
            System.out.println("Luego del Parseo: "+t2.toString()+"\n");
            
            assertEquals(t1, t2);
        } catch (ParseException ex) {
            Logger.getLogger(TestParseo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testParseModeloTerminal() {
        CoordenadaGlobal latitud = new CoordenadaGlobal(PuntoCardinal.Este, 54);
        CoordenadaGlobal longitud = new CoordenadaGlobal(PuntoCardinal.Sur, 32);
        UbicacionGeografica u1 = new UbicacionGeografica(latitud, longitud);
        ModeloTerminalRemota mt1 = new ModeloTerminalRemota(01, u1);
        mt1.addTipoSensor(FactorClimatico.humedad);
        mt1.addTipoSensor(FactorClimatico.humedad);
        mt1.addTipoSensor(FactorClimatico.presion);
        ModeloTerminalRemota mt2 = null;

        try {
            String str = mt1.toString();
            System.out.println("Terminal a String:\n" + str);
            mt2 = ModeloTerminalRemota.parse(str);
            System.out.println("Terminal parseada:\n"+mt2.toString());
        } catch (ParseException ex) {
            Logger.getLogger(TestParseo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(mt1, mt2);
    }


}