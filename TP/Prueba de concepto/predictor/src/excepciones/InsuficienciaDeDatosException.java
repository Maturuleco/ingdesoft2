/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package excepciones;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class InsuficienciaDeDatosException extends Exception{
    String message;

    public InsuficienciaDeDatosException() {
    }
    
    public InsuficienciaDeDatosException(String message) {
        this.message = message;
    }




}
