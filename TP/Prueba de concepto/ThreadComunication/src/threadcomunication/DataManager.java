/**
 * Created by IntelliJ IDEA.
 * User: Mar
 * Date: 25/10/2009
 * Time: 14:53:08
 * To change this template use File | Settings | File Templates.
 */
public class DataManager {

    private static String mensaje;
    public static void receiveData(String m){
        mensaje = m;
        System.out.println("si "+m);
    }
}
