/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import Datos.FactorClimatico;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mar
 */
public class TRsSubscriberMessage extends SubscriberMessage{

    private Date timeStamp;

    private List<Integer> TRs;
    private List<FactorClimatico> factoresClimaticos;

    public TRsSubscriberMessage(Integer idSuscriptor, Date timeStamp, List<Integer> TRs, List<FactorClimatico> factoresClimaticos) {
        this.idSuscriptor = idSuscriptor;
        this.timeStamp = timeStamp;
        this.TRs = TRs;
        this.factoresClimaticos = factoresClimaticos;
    }

    public List<Integer> getTRs() {
        return TRs;
    }

    public void setTRs(List<Integer> TRs) {
        this.TRs = TRs;
    }

    public List<FactorClimatico> getFactoresClimaticos() {
        return factoresClimaticos;
    }

    public void setFactoresClimaticos(List<FactorClimatico> factoresClimaticos) {
        this.factoresClimaticos = factoresClimaticos;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

}