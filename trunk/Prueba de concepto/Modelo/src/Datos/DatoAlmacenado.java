/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Datos;

import java.util.Date;

/**
 *
 * @author Santiago Avendaño
 */
public class DatoAlmacenado extends DatoSensado {
    private Integer idTR;
    private DataSource dataSource;

    public DatoAlmacenado(Integer idSensor, Date timeStamp, FactorClimatico factor, Float valor, Integer idTR, DataSource dataSource) {
        super(idSensor, timeStamp, factor, valor);
        this.idTR = idTR;
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }

    @Override
    public String toString(){
        return super.toString()+"_" + dataSource + "_" + idTR.toString();
    }

    public String mostrar(){
        String datoMostrado = new String();
        datoMostrado += "nº TR: " + idTR.toString() + "\n";
        datoMostrado += "Fuente: " + dataSource.name() + "\n";
        datoMostrado += super.motrar();
        return datoMostrado;
    }

}
