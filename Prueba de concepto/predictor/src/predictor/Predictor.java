/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import modelo.Regla;

/**
 *
 * @author Santiago Avendaño
 */
public abstract class Predictor{

    protected Regla regla;

    public Predictor(){
    }

    public Predictor(Regla regla) {
        this.regla = regla;
    }

    public Regla getRegla() {
        return regla;
    }

    public abstract ResultadoRegla analizar();

}