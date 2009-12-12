/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluador;

import RequerimientosModelos.RequerimientoResultado;
import java.util.Date;


/**
 *
 * @author Santiago Avendaño
 */
public class ResultadoEvaluacion {
    private Integer idModelo;
    private Integer idTR;
    private Integer reglasEvaluadas;
    private Integer reglasVerificadas;
    private Date timeStamp;

    public ResultadoEvaluacion() {
        timeStamp = new Date();
    }

    public ResultadoEvaluacion(Integer nombreModelo, Integer idTR, Integer reglasEvaluadas, Integer reglasVerificadas) {
        this.idModelo = nombreModelo;
        this.idTR = idTR;
        this.reglasEvaluadas = reglasEvaluadas;
        this.reglasVerificadas = reglasVerificadas;
        timeStamp = new Date();
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public Integer getReglasEvaluadas() {
        return reglasEvaluadas;
    }

    public void setReglasEvaluadas(Integer reglasEvaluadas) {
        this.reglasEvaluadas = reglasEvaluadas;
    }

    public Integer getReglasVerificadas() {
        return reglasVerificadas;
    }

    public void setReglasVerificadas(Integer reglasVerificadas) {
        this.reglasVerificadas = reglasVerificadas;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        String res = "";
        res += "["+timeStamp.toString()+"]";
        res += "Modelo: " + idModelo + " - ";
        res += "IDTR: " + idTR + " - ";
        res += "Resultado: " + reglasVerificadas.toString() + "/" + reglasEvaluadas.toString();
        return res;
    }

    public RequerimientoResultado obtenerRequerimiento(){
        return new RequerimientoResultado(null, idModelo, idTR);
    }
}
