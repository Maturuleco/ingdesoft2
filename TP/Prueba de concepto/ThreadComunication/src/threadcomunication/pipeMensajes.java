/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package threadcomunication;

import model.Mensaje;
/**
 *
 * @author matiaz
 */

public class pipeMensajes {
    private final static int size = 6;
    private Mensaje buffer[] = new Mensaje[size];

    private int elementos = 0;
    //El indice es para escribir, para leer es el anterior en el indice
    private int indice = 0;

    // Flags para saber el estado del buffer
    private boolean estaLlena = false;
    private boolean estaVacia = true;

    // Método para retirar letras del buffer
    public synchronized Mensaje read() {
        // No se puede consumir si el buffer está vacío
        while( estaVacia == true ) {
            try {
                wait(); // Se sale cuando estaVacia cambia a false
            } catch( InterruptedException e ) { }
        }
        // Decrementa la cuenta, ya que va a consumir una letra
        elementos--;
        // Comprueba si se retiró la última letra
        if( elementos == 0 )
            estaVacia = true;
        // El buffer no puede estar lleno, porque acabamos de consumir
        estaLlena = false;

        // Decremento el indice y devuelvo la letra al thread consumidor
        indice = (indice-1+size)%size;

        notify();
        return( buffer[indice] );
    }

    // Método para añadir letras al buffer
    public synchronized void write( Mensaje msj ) {
        // Espera hasta que haya sitio para otra letra
        while( estaLlena == true ) {
            try {
                wait(); // Se sale cuando estaLlena cambia a false
            } catch( InterruptedException e ) { }
        }
        // Añade una letra en el primer lugar disponible
        buffer[indice] = msj;
        indice = (indice+1)%size;
        // Cambia al siguiente lugar disponible
        elementos++;
        // Comprueba si el buffer está lleno
        if( elementos == size )
            estaLlena = true;
        estaVacia = false;
        notify();
    }

}
