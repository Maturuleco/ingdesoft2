/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author Santiago Avendaño
 */
public class DatoSensado {
    private Integer idSensor;
    private Date timeStamp;
    private FactorClimatico factor;
    private Float valor;

    public FactorClimatico getFactor() {
        return factor;
    }

    public void setFactor(FactorClimatico factor) {
        this.factor = factor;
    }

    public Integer getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Integer idSensor) {
        this.idSensor = idSensor;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
    
}
