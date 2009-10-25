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
            Date date = new Date(Date.parse(partes[1]));
            FactorClimatico fc = FactorClimatico.parse(partes[2]);
            Float valor = Float.parseFloat(partes[3]);

            return new DatoSensado(idSensor, date, fc, valor);
        }
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
