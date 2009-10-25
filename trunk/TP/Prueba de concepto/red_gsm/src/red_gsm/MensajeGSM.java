package red_gsm;

import java.util.Date;

public class MensajeGSM {
	private Integer destino;
	private Integer origen;
	private String mensaje;
	private Date fecha;
	
	public MensajeGSM(int orig, int dest, String msj)
	{
		destino = dest;
		origen = orig;
		mensaje = new String(msj);
		fecha = new Date();
		/*
                // Lo que sobra lo tira...
                mensaje = new char[160];
                int lon = msj.length();
		for (int i=0; i < mensaje.length(); i++) {
			if (lon >= i)
		   	    mensaje[i] = msj[i] ;
			else
				mensaje[i] = ' ';
		}
                */
	}
	
	public Integer getDestino()
	{
		return destino;
	}
	
	public Integer getOrigen()
	{
		return origen;
	}
	
	public String getMensaje()
	{
		return mensaje;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
}
