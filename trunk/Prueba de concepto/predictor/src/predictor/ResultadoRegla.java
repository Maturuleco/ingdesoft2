/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package predictor;

/**
 *
 * @author Santiago Avendaño
 */
public class ResultadoRegla {

    private Integer condicionesVerificadas;
    private Integer condicionesNoAnalizadas;
    private Integer condicionesNoVerificadas;

    public ResultadoRegla(Integer condicionesVerificadas, Integer condicionesNoAnalizadas, Integer condicionesNoVerificadas) {
        this.condicionesVerificadas = condicionesVerificadas;
        this.condicionesNoAnalizadas = condicionesNoAnalizadas;
        this.condicionesNoVerificadas = condicionesNoVerificadas;
    }

    public Integer cantidadCondiciones() {
        return condicionesVerificadas + condicionesNoAnalizadas + condicionesNoVerificadas;
    }

    public Boolean verifiqueTodasLasCondiciones() {
        return condicionesVerificadas.equals(cantidadCondiciones());
    }

    @Override
    public String toString() {
        String res = "";
        res += "Resultado: ";
        res += "Verificadas: " + condicionesVerificadas.toString()+ " | ";
        res += "No Analizadas: " + condicionesNoAnalizadas.toString() + " | ";
        res += "No Verificadas: " + condicionesNoVerificadas.toString();
        return res;
    }


}
