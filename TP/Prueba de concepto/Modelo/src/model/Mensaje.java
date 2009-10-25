/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import red_gsm.MensajeGSM;
/**
 *
 * @author Santiago Avendaño
 */
public class Mensaje {
    Integer idTR;
    DataSource dataSource;
    List<DatoSensado> datos;

    public Mensaje(MensajeGSM m) throws ParseException {
        String cuerpo = new String(m.getMensaje());
        String[] partes = cuerpo.split("|");

        if (partes.length < 2)
            throw new ParseException(cuerpo, 0);
        
        idTR = Integer.parseInt(partes[0]);
        dataSource = DataSource.parse(partes[1]);
        datos = new LinkedList<DatoSensado>();
        for (int i = 2; i < partes.length; i++)
            datos.add(DatoSensado.parse(partes[i]));
    }

    @Override public String toString(){
        String id = Integer.toString(idTR);
        String ds = dataSource.toString();
        String res = id+"|"+ds;
        for (DatoSensado datoSensado : datos) {
            res += "|"+(datoSensado.toString());
        }
        return res;
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
