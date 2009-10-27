package red_gsm;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Date;

public class RedGSM {
    private static final String path = "GSM";
    private Dictionary< Integer , File > modems;
    private File carpeta;
    private static RedGSM instancia = null;
	
    private RedGSM() {
        modems = new Hashtable< Integer, File >();
        carpeta = new File(path);
        carpeta.mkdir();
        modems.put(0, carpeta);
    }

    public static RedGSM getInstance() {
        if(instancia == null)
            instancia = new RedGSM();
        return instancia;
    }
	
   
}
