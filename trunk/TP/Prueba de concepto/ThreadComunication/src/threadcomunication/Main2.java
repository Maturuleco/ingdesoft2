/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threadcomunication;

import model.Mensaje;

/**
 *
 * @author tas
 */
public class Main2 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ColaSync<Mensaje> cMensaje = new ColaSync<Mensaje>();
        ColaSync<String> cString = new ColaSync<String>();
        final int numEscritores = 10;
        final int numLectores = 1;
        final int cantMsjXEscritor = 100;
        
        System.out.println("////EMPIEZA TEST CON STRING//////");
        
        Escritor[] escritores = new Escritor[numEscritores];
        Lector[] lectores = new Lector[numLectores];
        
        int i = 1;
        for (Lector lector : lectores) {
            lector = new Lector(i, cString);
            lector.start();
            i++;
        }
        
        i = 1;
        for (Escritor escritor : escritores) {
            escritor = new Escritor(i, cString, cantMsjXEscritor);
            escritor.start();
            i++;
        }
    }
    
    
}
