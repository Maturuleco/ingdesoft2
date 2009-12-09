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

}
