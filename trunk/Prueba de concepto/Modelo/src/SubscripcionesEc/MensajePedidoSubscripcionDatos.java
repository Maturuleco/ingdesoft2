package SubscripcionesEc;

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
        boolean eq = true;
        if (obj == null) {
            eq =false;
        }
        if (getClass() != obj.getClass()) {
            //System.out.println("distinto: " + this.getClass()+" != " +obj.getClass());
            eq=false;
        }
        final MensajePedidoSubscripcionDatos other = (MensajePedidoSubscripcionDatos) obj;
        
        if (this.tr != other.tr && (this.tr == null || !this.tr.equals(other.tr))) {
            //System.out.println("distinto: " + this.tr+" != " +other.tr);
            eq =false;
        }
        if (this.factorClimatico != other.factorClimatico) {
            //System.out.println("distinto: " + this.factorClimatico+" != " +other.factorClimatico);
            eq=false;
        }
//        if(!eq)
//            System.out.println("distinto: " + other.toString() +" != " +toString());
        return eq;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.tr != null ? this.tr.hashCode() : 0);
        hash = 73 * hash + this.factorClimatico.hashCode();
        return hash;
    }


 @Override
    public String toString(){
        return "<" +tr +", "+ factorClimatico+ ">";
    }


}
