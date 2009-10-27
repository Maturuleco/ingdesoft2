/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TR;

/**
 *
 * @author Administrador
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void adic(Integer num) {
        num++;
    }

    public static void formatPartes(String[] partes) {
        for (int i = 0; i < partes.length-1; i++) {
            String msj = partes[i];
            // Le pongo un Id al Mensaje en su cuerpo
            // Tb le pongo una M (more) para decir que
            // no es la última fracción
            msj = "M#"+i+"#"+msj;
            partes[i] = msj;
        }
        // Hago uno para el último
        int i = partes.length - 1;
        String msj = partes[i];
        // L de Last
        msj = "L#"+i+"#"+msj;
        partes[i] = msj;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        String res = "Hola";
        res += " Manola";
        System.out.println(res);
        Integer qonda = new Integer(33);
        Integer hola = qonda + 1;
        System.out.println(qonda);
        System.out.println(hola);
        adic(qonda);
        System.out.println((0-1+6)%6);
        
        String[] test = new String[3];
        test[0] = "hola";
        test[1] = "chau";
        test[2] = "adios";
        formatPartes(test);
        System.out.println("\n Array de String: \n");
        System.out.println("[");
        for (String string : test) {
            System.out.println(string+",");
        }
        System.out.println("]\n");

        
    }
    
}
