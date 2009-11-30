/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analizador;

import evaluador.ResultadoEvaluacion;
import java.util.Collection;
import modelo.Modelo;

/**
 *
 * @author Santiago Avendaño
 */
public class Analizador {

    private AnalizadorDAO analizadorDAO = new AnalizadorDAO();

    public AnalizadorDAO getAnalizadorDAO() {
        return analizadorDAO;
    }

    public void analizar(Modelo modelo, Collection<ResultadoEvaluacion> resultados){
        analizadorDAO.escribirResultados(resultados);
    }


}
