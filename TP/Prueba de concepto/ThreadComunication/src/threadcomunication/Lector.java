/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threadcomunication;

/**
 *
 * @author tas
 */
public class Lector extends Thread {
    private static final int waitTime = 10;
    private int id;
    private ColaSync c;

    public Lector(int ident, ColaSync cola){
        id = ident;
        c = cola;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Espero 1/2 segundo
                sleep(waitTime);
            } catch (InterruptedException ex) { }
            Object m = c.get();
            String msj = "Lector "+id+" lee:\n\t"+m+"\n";
            System.out.println(msj);
        }
    }

}