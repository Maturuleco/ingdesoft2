/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Santiago Avendaño <santiavenda2@gmail.com>
 */
public class Alerta {
    private Integer idTR;
    private String mensaje;

    public Alerta(Integer idTR, String mensaje) {
        this.idTR = idTR;
        this.mensaje = mensaje;
    }

    public Integer getIdTR() {
        return idTR;
    }

    public void setIdTR(Integer idTR) {
        this.idTR = idTR;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
