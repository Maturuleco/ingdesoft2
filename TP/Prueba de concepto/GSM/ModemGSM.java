package GSM;

import GSM.RedGSM;
import GSM.MensajeGSM;

public class ModemGSM {
	private int numero;
	private RedGSM red;
	
	public ModemGSM(int num, RedGSM redGSM )
	{
		numero = num;
		red = redGSM;
	}
	
	public void send(int destino, char[] cuerpo) 
	{
		MensajeGSM msj = new MensajeGSM(numero, destino, cuerpo);
		red.send(msj);
		
	}
	
	//TODO: public Mensaje recibir()
	
}
