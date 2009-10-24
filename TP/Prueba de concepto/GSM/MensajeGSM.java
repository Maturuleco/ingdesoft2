package GSM;

import java.util.Date;

public class MensajeGSM {
	private Integer destino;
	private Integer origen;
	private char[] mensaje;
	private Date fecha;
	
	public MensajeGSM(int orig, int dest, char[] msj)
	{
		destino = dest;
		origen = orig;
		mensaje = new char[160];
		int lon = msj.length;
		fecha = new Date();
		
		for (int i=0; i < mensaje.length; i++) {
			if (lon >= i)
		   	    mensaje[i] = msj[i] ;
			else
				mensaje[i] = ' ';
		}
	}
	
	public Integer getDestino()
	{
		return destino;
	}
	
	public Integer getOrigen()
	{
		return origen;
	}
	
	public char[] getMensaje()
	{
		return mensaje;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
}
