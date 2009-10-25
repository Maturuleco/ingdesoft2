/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.sun.org.apache.xpath.internal.operations.Equals;
import java.text.ParseException;
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

    public Mensaje(Integer id, DataSource ds) {
        idTR = id;
        dataSource = ds;
        datos = new LinkedList<DatoSensado>();
    }

    public Mensaje makeFromSMS(MensajeGSM m) throws ParseException {
        String cuerpo = new String(m.getMensaje());
        return Mensaje.parse(cuerpo);
    }

    public static Mensaje parse(String cuerpo) throws ParseException {
        String[] partes = cuerpo.split("\\|");

        if (partes.length < 2)
            throw new ParseException(cuerpo, 0);

        Integer id = Integer.valueOf(partes[0]);
        DataSource dataS = DataSource.parse(partes[1]);

        Mensaje msj = new Mensaje(id, dataS);

        for (int i = 2; i < partes.length; i++)
            msj.addDato(DatoSensado.parse(partes[i]));

        return msj;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mensaje other = (Mensaje) obj;
        if (this.idTR != other.idTR && (this.idTR == null || !this.idTR.equals(other.idTR))) {
            return false;
        }
        if (this.dataSource != other.dataSource) {
            return false;
        }
        if (this.datos != other.datos && (this.datos == null || !this.datos.equals(other.datos))) {
            return false;
        }
        return true;
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

    public void addDato(DatoSensado d) {
        datos.add(d);
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }
}
