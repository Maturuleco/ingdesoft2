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

public class Adapter extends Thread{

    private FileChannel channel;
    private long frequency;

    public Adapter(FileChannel channel, String nombre, long frequency) {
        super(nombre);
        this.channel = channel;
        this.frequency = frequency;
    }

    public void run() {
        String phrase = getName();

        for(int i = 0; true; ++i){
            try {
                if (i%frequency==0){
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int numRead = 0;
                    String mensaje = "";
                    while (numRead >= 0) {
                        buf.rewind();
                        numRead = channel.read(buf);
                        System.out.println("num "+numRead);
                        buf.rewind();
                        for (int m=0; m<numRead; m++) {
                            byte b = buf.get();
                            mensaje+=String.valueOf(b);
                            
                        }
                        if (numRead > 0)
                            DataManager.receiveData(mensaje);
                        //buf.clear();
                    }

                }
            } catch (Exception e) {
              e.printStackTrace(System.err);
            }
        }
      }
}
