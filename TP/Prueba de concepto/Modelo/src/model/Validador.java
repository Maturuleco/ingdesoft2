/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author tas
 */
public class Validador {
    
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

    public static Boolean checkHash(String hash, String Mensaje) {
        String recalculatedHash = getHash(Mensaje);
        return recalculatedHash.equals(Mensaje);
    }
}
