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


}
