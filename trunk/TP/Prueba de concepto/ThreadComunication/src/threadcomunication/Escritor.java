/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threadcomunication;

/**
 *
 * @author tas
 */
public class Escritor extends Thread {
    private static final int waitTime = 100;
    private static int cantMsj;
    private int id;
    private ColaSync c;

    public Escritor(int ident, ColaSync cola, int cantidadDeMensajes){
        id = ident;
        c = cola;
        cantMsj = cantidadDeMensajes;
    }

    @Override
    public void run() {
        for (int i = 0; i < cantMsj; i++) {

            try {
                // Espero 1/2 segundo
                sleep(waitTime);
            } catch (InterruptedException ex) { }

            String msj = "Escritor " + id + " escribe " + i + "\n";
            System.out.println(msj);
            c.put(msj);

        }
    }

}