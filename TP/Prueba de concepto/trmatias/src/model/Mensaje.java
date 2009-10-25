/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.List;

import gsm.MensajeGSM;

/**
 *
 * @author Santiago Avendaño
 */
public class Mensaje {
    Integer idTR;
    DataSource dataSource;
    List<DatoSensado> datos;

    public Mensaje(MensajeGSM m) {
        String cuerpo = new String(m.getMensaje());
        String[] partes = cuerpo.split("|");
        //TODO: mejorar la liea de abajo!!!
        assert(partes.length < 2);
        idTR = Integer.parseInt(partes[1]);
        if(partes[2].equals("E"));






    }



    @Override
    public String toString() {
        return "si,tu";
    }

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
