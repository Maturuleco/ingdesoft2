package red_gsm;


public class ModemGSM {
	private int numero;
	
	public ModemGSM(int num)
	{
		numero = num;
	}
	
	public void send(int destino, String cuerpo)
	{
            MensajeGSM msj = new MensajeGSM(numero, destino, cuerpo);
            RedGSM.getInstance().send(msj);
	}

        private MensajeGSM recive() {
            // TODO !!!!!!!!!!!
            return null;
        }

        
	//TODO: run()
	
}
