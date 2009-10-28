/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author Santiago Avendaño
 */
public class DatoSensado{
    private Integer idSensor;
    private Date timeStamp;

    private FactorClimatico factor;
    private Float valor;

    public DatoSensado(Integer idSensor, Date timeStamp, FactorClimatico factor, Float valor) {
        this.idSensor = idSensor;
        this.timeStamp = timeStamp;
        this.factor = factor;
        this.valor = valor;
    }

    public static DatoSensado parse(String dato) throws ParseException{
        String[] partes = dato.split("_");
        if (partes.length != 4)
            throw new ParseException(dato, 0);
        else {
            Integer idSensor = Integer.parseInt(partes[0]);
            Date date = new Date(Long.valueOf(partes[1]));
            FactorClimatico fc = FactorClimatico.parse(partes[2]);
            Float valor = Float.parseFloat(partes[3]);

            return new DatoSensado(idSensor, date, fc, valor);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatoSensado other = (DatoSensado) obj;
        if (this.idSensor != other.idSensor && (this.idSensor == null || !this.idSensor.equals(other.idSensor))) {
            return false;
        }
        if (this.timeStamp != other.timeStamp && (this.timeStamp == null || !this.timeStamp.equals(other.timeStamp))) {
            return false;
        }
        if (this.factor != other.factor) {
            return false;
        }
        if (this.valor != other.valor && (this.valor == null || !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.idSensor != null ? this.idSensor.hashCode() : 0);
        hash = 83 * hash + (this.timeStamp != null ? this.timeStamp.hashCode() : 0);
        hash = 83 * hash + this.factor.hashCode();
        hash = 83 * hash + (this.valor != null ? this.valor.hashCode() : 0);
        return hash;
    }

    @Override public String toString(){
        String idS = this.idSensor.toString();
        String date = timeStamp.toString();
        String fc = factor.toString();
        String val = valor.toString();
        return idS+"_"+date+"_"+fc+"_"+val;
    }

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
