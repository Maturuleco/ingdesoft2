/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemapruebapredictor;

import java.util.Calendar;
import java.util.Date;
import model.DataSource;
import model.DatoAlmacenado;
import model.FactorClimatico;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class GeneradorDatosOrdenados implements GeneradorDatos{

    public enum Orden {
        creciente,
        decreciente,
        constante;
    }
    
    private final Float inicio;
    private final Float fin;
    private Orden orden;
    private Float actual;
    private Float escala;
    private FactorClimatico factor;
    private Integer idTR;
    private Integer idSensor;

    public GeneradorDatosOrdenados( Orden orden, Float inicio, Float escala, Float fin, Integer idTR, Integer idSensor)
            throws InstantiationException {
        this.inicio = inicio;
        this.orden = orden;
        this.actual = inicio;
        this.escala = escala;
        this.idTR = idTR;
        this.idSensor = idSensor;
        factor = FactorClimatico.temperatura;
        this.fin = fin;
        switch(orden){
            case creciente:
                if (inicio > fin) throw new IllegalArgumentException("el valor inicio debe ser menor que fin");
                break;
            case decreciente:
                if (inicio < fin) throw new IllegalArgumentException("el valor inicio debe ser mayor que fin");
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public DatoAlmacenado generarDato() {
        Date ahora= Calendar.getInstance().getTime();
        switch(orden){
            case creciente:
                this.actual += this.escala;
                if (this.actual > this.fin) this.actual = this.inicio;
                break;
            case decreciente:
                this.actual -= this.escala;
                if (this.actual < this.fin) this.actual = this.inicio;
                break;
            case constante:
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return new DatoAlmacenado(idSensor, ahora, factor, actual, idTR, DataSource.terminal_remota);
    }

}
