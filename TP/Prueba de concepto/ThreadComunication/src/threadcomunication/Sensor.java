/*
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 24/10/2009
 * Time: 16:20:54
 * To change this template use File | Settings | File Templates.
 */
package threadcomunication;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Sensor extends Thread{

    private FileChannel channel;
    private long frequency;

    public Sensor(FileChannel channel, String nombre, long frequency) {
        super(nombre);
        this.channel = channel;
        this.frequency = frequency;
    }

    public void run() {
        String phrase = getName();
        int i = 1;
        while(true){
  //  for (int j = 0; j < 2; j++) {
            
            try {
                if (i == frequency){
                    i=0;
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    for (char ch : phrase.toCharArray()) {
                      buf.putChar(ch);
                    }
                    buf.flip();
                    channel.write(buf);
                    
                }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
            i++;
        }
    }
}
