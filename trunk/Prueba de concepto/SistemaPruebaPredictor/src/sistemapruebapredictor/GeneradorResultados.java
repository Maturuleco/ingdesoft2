/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemapruebapredictor;

import evaluador.ResultadoEvaluacion;

/**
 *
 * @author Administrador
 */
public class GeneradorResultados {

    public enum Orden {
        creciente,
        decreciente,
        constante;
    }

    private Orden orden;
    private Integer inicio;
    private Integer actual;
    private Integer escala;
    private Integer reglasTotales;
    private Integer idModelo;
    private Integer idTR;

    public GeneradorResultados(Orden orden, Integer inicio, Integer escala, Integer reglasTotales, Integer idModelo, Integer idTR) {
        this.actual = inicio;
        this.orden = orden;
        this.inicio = inicio;
        this.escala = escala;
        this.reglasTotales = reglasTotales;
        this.idModelo = idModelo;
        this.idTR = idTR;
         switch (orden) {
            case creciente:
                if (inicio > reglasTotales) {
                    throw new IllegalArgumentException("el valor inicio debe ser menor que fin");
                }
                break;
            case decreciente:
                if (inicio < 0) {
                    throw new IllegalArgumentException("el valor inicio debe ser mayor que fin");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public ResultadoEvaluacion generarDato() {
        switch (orden) {
            case creciente:
                this.actual += this.escala;
                if (this.actual > this.reglasTotales) {
                    this.actual = this.inicio;
                }
                break;
            case decreciente:
                this.actual -= this.escala;
                if (this.actual < 0) {
                    this.actual = this.inicio;
                }
                break;
            case constante:
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return new ResultadoEvaluacion(idModelo, idTR, reglasTotales, actual);
    }
}
