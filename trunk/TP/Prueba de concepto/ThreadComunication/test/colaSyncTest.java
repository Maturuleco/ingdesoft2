/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensaje;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import threadcomunication.ColaSync;
import static org.junit.Assert.*;

/**
 *
 * @author tas
 */
public class colaSyncTest {

    private ColaSync<Mensaje> cMensaje = new ColaSync<Mensaje>();
    private ColaSync<String> cString = new ColaSync<String>();
    
    private class Escritor extends Thread {
        private int id;
        private ColaSync c;
        
        public Escritor(int ident, ColaSync cola){
            id = ident;
            c = cola;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                
                try {
                    // Espero 1/2 segundo
                    sleep(500);
                } catch (InterruptedException ex) { }

                String msj = "Escritor " + id + " escribe " + i + "\n";
                System.out.println(msj);
                c.put(msj);
                
            }
        }
        
    }

    private class Lector extends Thread {
        private int id;
        private ColaSync c;
        
        public Lector(int ident, ColaSync cola){
            id = ident;
            c = cola;
        }
        
        @Override
        public void run() {
            System.out.println("Entra lector\n");
            while (true) {
                try {
                    // Espero 1/2 segundo
                    sleep(500);
                } catch (InterruptedException ex) { }
                System.out.println("Lector Pasa el sleep\n");
                Object m = c.get();
                String msj = "Lector "+id+" lee:\n\t"+m+"\n";
                System.out.println(msj);
            }
        }
        
    }
    
    public colaSyncTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testConString() {
        final int numEscritores = 4;
        final int numLectores = 2;
        Escritor[] escritores = new colaSyncTest.Escritor[numEscritores];
        Lector[] lectores = new colaSyncTest.Lector[numLectores];
        
        int i = 1;
        for (colaSyncTest.Lector lector : lectores) {
            lector = new Lector(i, cString);
            lector.start();
            i++;
        }
        
        i = 1;
        for (colaSyncTest.Escritor escritor : escritores) {
            escritor = new Escritor(i, cString);
            escritor.start();
            i++;
        }

    }

}