package SubscripcionesEc;

import model.*;
import java.util.Date;

/**
 *
 * @author Ce y Mat
 */
public class MensajePedidoSubscripcionResultados extends SubscriberMessage{

    private Date timeStamp;

    private Integer modelo;
    private Integer trID;

    public MensajePedidoSubscripcionResultados(Integer idSuscriptor, Integer ecProovedora, Integer modelo, Integer trID) {
        super(idSuscriptor, ecProovedora);
        this.timeStamp = new Date();
        this.modelo = modelo;
        this.trID = trID;
    }

    public Integer getModelo() {
        return modelo;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
    
    public Integer getTrID() {
        return trID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MensajePedidoSubscripcionResultados other = (MensajePedidoSubscripcionResultados) obj;
        if (this.timeStamp != other.timeStamp && (this.timeStamp == null || !this.timeStamp.equals(other.timeStamp))) {
            return false;
        }
        if (this.modelo != other.modelo && (this.modelo == null || !this.modelo.equals(other.modelo))) {
            return false;
        }
        if (this.trID != other.trID && (this.trID == null || !this.trID.equals(other.trID))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

}
