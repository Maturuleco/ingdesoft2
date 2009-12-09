package model;

import java.util.Date;

/**
 *
 * @author Ce y Mat
 */
public class MensajePedidoSubscripcionResultados extends SubscriberMessage{

    private Date timeStamp;

    private Integer modelo;
    private Integer trID;

    public MensajePedidoSubscripcionResultados(Integer modelo, Integer trID) {
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

}
