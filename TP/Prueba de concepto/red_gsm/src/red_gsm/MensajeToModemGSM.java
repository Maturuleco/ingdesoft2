/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package red_gsm;

import java.util.Comparator;

/**
 *
 * @author tas
 */

public class MensajeToModemGSM {
	private Integer destino;
	private String mensaje;
        private Integer priority = 0;
		
	public MensajeToModemGSM(int dest, String msj)
	{
		destino = dest;
		mensaje = new String(msj);
	}

        public MensajeToModemGSM(int dest, String msj, int priority)
	{
		destino = dest;
		mensaje = new String(msj);
                this.priority = priority;
	}

	public Integer getDestino()
	{
		return destino;
	}
	
	public String getMensaje()
	{
		return mensaje;
	}

        public Integer getPriority() {
            return priority;
        }

        public class Comparador implements Comparator<MensajeToModemGSM> {

            public int compare(MensajeToModemGSM m1, MensajeToModemGSM m2) {
                return (m1.getPriority() - m2.getPriority());
            }
        }
}
