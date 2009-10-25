import java.io.*;
import java.nio.channels.*;

/**
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 24/10/2009
 * Time: 16:26:29
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String [] args){
        String path = "C:/Documents and Settings/Mar/Escritorio/red.txt";
        File aFile = new File(path);
        FileOutputStream outputFile = null;
        try {
          outputFile = new FileOutputStream(aFile, true);
        } catch (FileNotFoundException e) {
          e.printStackTrace(System.err);
        }

        new Sensor(outputFile, "t", 100).start();
        new Sensor(outputFile, "h", 1000).start();
        new Sensor(outputFile, "p", 10000).start();
    }
}
