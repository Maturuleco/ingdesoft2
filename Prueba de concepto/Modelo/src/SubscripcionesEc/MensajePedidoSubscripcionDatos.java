package SubscripcionesEc;

import model.*;
import Datos.FactorClimatico;
import java.util.Date;

/**
 *
 * @author mar
 */
public class MensajePedidoSubscripcionDatos extends SubscriberMessage{

    private Date timeStamp;

    private Integer tr;
    private FactorClimatico factorClimatico;

    public MensajePedidoSubscripcionDatos(Integer idSuscriptor, Integer ecProovedora, Integer tr, FactorClimatico factorClimatico) {
        super(idSuscriptor, ecProovedora);
        this.timeStamp = new Date();
        this.tr = tr;
        this.factorClimatico = factorClimatico;
    }

    public Integer getTR() {
        return tr;
    }

    public FactorClimatico getFactorClimatico() {
        return factorClimatico;
    }

    
    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MensajePedidoSubscripcionDatos other = (MensajePedidoSubscripcionDatos) obj;
        if (this.timeStamp != other.timeStamp && (this.timeStamp == null || !this.timeStamp.equals(other.timeStamp))) {
            return false;
        }
        if (this.tr != other.tr && (this.tr == null || !this.tr.equals(other.tr))) {
            return false;
        }
        if (this.factorClimatico != other.factorClimatico) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }



}
