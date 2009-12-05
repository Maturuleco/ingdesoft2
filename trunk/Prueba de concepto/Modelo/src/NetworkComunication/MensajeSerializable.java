/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package NetworkComunication;

import model.*;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Ce y Mat
 */

public class MensajeSerializable implements MensajeGeneral, Serializable{
    private int priority = 0;
    long timeStamp;
    Integer idTR;
    DataSource dataSource;
    List<DatoSensado> datos;

    public MensajeSerializable(Integer id, DataSource ds) {
        Date d = new Date();
        timeStamp = d.getTime();
        idTR = id;
        dataSource = ds;
        datos = new LinkedList<DatoSensado>();
    }


    public MensajeSerializable(Integer id, DataSource ds, int priority) {
        Date d = new Date();
        timeStamp = d.getTime();
        idTR = id;
        dataSource = ds;
        datos = new LinkedList<DatoSensado>();
        this.priority = priority;
    }

    public static MensajeSerializable parse(String cuerpo) throws ParseException {
        String[] partes = cuerpo.split("\\|");

        if (partes.length < 3)
            throw new ParseException(cuerpo, 0);
        
        Integer id = Integer.valueOf(partes[0]);
        DataSource dataS = DataSource.parse(partes[1]);

        MensajeSerializable msj = new MensajeSerializable(id, dataS);
        msj.setTimeStamp(Long.valueOf(partes[2]));

        for (int i = 3; i < partes.length; i++)
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
        final MensajeSerializable other = (MensajeSerializable) obj;
        if (this.timeStamp != other.timeStamp) {
            return false;
        }
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

    @Override 
    public String toString(){
        String id = Integer.toString(idTR);
        String ds = dataSource.toString();
        String ts = Long.toString(timeStamp);
        String res = id+"|"+ds+"|"+ts;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getPriority() {
        return priority;
    }

    public class Comparador implements Comparator<MensajeSerializable> {

        public int compare(MensajeSerializable m1, MensajeSerializable m2) {
            return (m1.getPriority() - m2.getPriority());
        }
    }
    
}
