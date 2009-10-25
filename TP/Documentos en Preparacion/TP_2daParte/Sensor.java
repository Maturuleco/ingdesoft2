/**
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 24/10/2009
 * Time: 16:20:54
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Sensor extends Thread{

    private FileOutputStream stream;
    private long frequency;

    public Sensor(FileOutputStream stream, String nombre, long frequency) {
        super(nombre);
        this.stream = stream;
        this.frequency = frequency;
    }

    public void run() {
        String phrase = getName();
        FileChannel outChannel = stream.getChannel();

        for(int i = 0; true; ++i){
            try {
                if (i%frequency==0){
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    for (char ch : phrase.toCharArray()) {
                      buf.putChar(ch);
                    }
                    buf.flip();
                    outChannel.write(buf);
                }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
        }
      }    
}
