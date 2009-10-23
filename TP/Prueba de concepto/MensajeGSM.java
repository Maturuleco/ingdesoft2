
public class MensajeGSM {
	private int destino;
	private int origen;
	private char[] mensaje;
	
	public MensajeGSM(int orig, int dest, char[] msj)
	{
		destino = dest;
		origen = orig;
		mensaje = new char[160];
		int lon = msj.length;
		
		for (int i=0; i < mensaje.length; i++) {
			if (lon >= i)
		   	    mensaje[i] = msj[i] ;
			else
				mensaje[i] = ' ';
		}
	}
	
	public int getDestino()
	{
		return destino;
	}
	
	public int getOrigen()
	{
		return origen;
	}
	
	public char[] getMensaje()
	{
		return mensaje;
	}
	
}
