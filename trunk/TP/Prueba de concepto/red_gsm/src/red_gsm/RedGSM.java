package red_gsm;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Date;

public class RedGSM {
    private int numModem;
    private Dictionary< Integer , File > modems;
    private File carpeta;
    private static RedGSM instancia = null;
	
    private RedGSM() {
        modems = new Hashtable< Integer, File >();
        numModem = 1;
        carpeta = new File("GSM");
        carpeta.mkdir();
        modems.put(0, carpeta);
    }

    public static RedGSM getInstance() {
        if(instancia == null)
            instancia = new RedGSM();
        return instancia;
    }
	
    public ModemGSM createModem()
    {
	File dir = new File(String.valueOf(numModem));
	dir.mkdir();
	modems.put(numModem, dir);
	numModem++;
	ModemGSM modem = new ModemGSM(numModem);
	return modem;
    }
	
    public void send(MensajeGSM msj)
    {
        File carpetaDst = modems.get(msj.getDestino());
	String nombre = carpetaDst.getName() + "/" ;
	Date fechaMsj = msj.getFecha();
	
	//La idea es que el nombre no esté repetido...
	//String nombre = msj.getOrigen().toString() + "-" + fechaMsj.hashCode();
	nombre += msj.getOrigen().toString() + "-" + fechaMsj.getTime();
	nombre += ".txt";

	try {
            /*
            File mensaje = new File(nombre);
            FileWriter archivo = new FileWriter(mensaje);
            */
            FileWriter fstream;
            fstream = new FileWriter(nombre);
            BufferedWriter out = new BufferedWriter(fstream);
		
            out.write("De: "+msj.getOrigen().toString());
            out.write("A: "+msj.getDestino().toString());
	    //No le gusta lo de Date porque dice que está fuera de uso...
//	    String fechaGMT = fechaMsj.toGMTString();
//            out.write("Fecha: " + fechaGMT );
	    out.write(msj.getMensaje());
		    
	    out.close();
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}		
    }
}
