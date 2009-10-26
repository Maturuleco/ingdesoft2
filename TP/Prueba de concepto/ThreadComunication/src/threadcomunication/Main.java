
package threadcomunication;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Main {

    public static void main(String [] args){
        String dir = "./red/";

        for (int i = 1; i <= 1; ++i){
            try{
                String path = dir+"sensor"+i+".txt";
                File file = new File(path);
                FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
                new Sensor(channel, "s"+i, 50*i).start();
   //             new Adapter(channel, "a"+i, 500*i).start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private static FileOutputStream createOutputFile(String path){
        File file = new File(path);
        FileOutputStream outputFile = null;
        try {
          outputFile = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
          e.printStackTrace(System.err);
        }
        return outputFile;
    }
}
