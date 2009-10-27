/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

/**
 *
 * @author tas
 */

public class MensajeToModemGSM {
	private Integer destino;
	private String mensaje;
		
	public MensajeToModemGSM(int dest, String msj)
	{
		destino = dest;
		mensaje = new String(msj);
	}
	
	public Integer getDestino()
	{
		return destino;
	}
	
	public String getMensaje()
	{
		return mensaje;
	}
}
