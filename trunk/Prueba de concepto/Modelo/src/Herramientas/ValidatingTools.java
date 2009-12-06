/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Herramientas;

import Datos.DataSource;
import model.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author tas
 */
public class ValidatingTools {
    
    public static Boolean validar(String mensaje, DataSource origen) {
        // TODO: hacer el cuerpo del validador de mensajes
        return true;
    }

    public static String getHash(String mensaje){
        String hash = "";
        try{
            byte[] buffer = mensaje.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(buffer);
            byte[] digest = md.digest();
            for (byte aux : digest) {
                int b = aux & 0xff;
                if (Integer.toHexString(b).length() == 1) {
                    hash += "0";
                }
                hash += Integer.toHexString(b);
            }
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace(System.err);
        }
        return hash;
    }

    public static Boolean checkHash(String[] msj) {
        String hash = msj[msj.length-1];
        String mensaje = recuperarMensaje(msj);
        String recalculatedHash = getHash(mensaje);
        return recalculatedHash.equals(hash);
    }
    
    private static String recuperarMensaje(String[] msj) {
        if (msj.length < 1)
            return "";
        String res = msj[0];
        for (int i = 1; i < msj.length-1; i++) {
            res += "#" + msj[i];
        }
        return res;

    }
}
