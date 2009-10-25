/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;

/**
 *
 * @author Santiago Avendaño
 */
public class Mensaje {
    Integer idTR;
    DataSource dataSource;
    List<DatoSensado> datos;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<DatoSensado> getDatos() {
        return datos;
    }

    public void setDatos(List<DatoSensado> datos) {
        this.datos = datos;
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }
}
