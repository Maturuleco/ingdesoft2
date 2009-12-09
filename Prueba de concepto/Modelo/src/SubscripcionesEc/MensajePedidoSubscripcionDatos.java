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

    public MensajePedidoSubscripcionDatos(Integer idSuscriptor, Integer tr, FactorClimatico factorClimatico) {
        this.idSuscriptor = idSuscriptor;
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

}
