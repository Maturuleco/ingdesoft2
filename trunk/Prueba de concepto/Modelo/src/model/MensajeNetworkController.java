
package model;

import java.util.Date;

public class MensajeNetworkController {

    protected Integer idTR;
    protected Date fecha;

     public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }
}
