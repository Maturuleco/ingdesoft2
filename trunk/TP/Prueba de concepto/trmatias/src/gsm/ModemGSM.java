package gsm;

import gsm.RedGSM;
import gsm.MensajeGSM;

public class ModemGSM {
	private int numero;
	private RedGSM red;
	
	public ModemGSM(int num, RedGSM redGSM )
	{
		numero = num;
		red = redGSM;
	}
	
	public void send(int destino, String cuerpo)
	{
		MensajeGSM msj = new MensajeGSM(numero, destino, cuerpo);
		red.send(msj);
		
	}
	
	//TODO: public MensajeGSM recive/run()
	
}
