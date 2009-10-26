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
    }

}
