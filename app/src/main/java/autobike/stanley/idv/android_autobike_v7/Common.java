package autobike.stanley.idv.android_autobike_v7;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Common {

//    public static String URL = "http://192.168.196.189:8080/Spot_MySQL_Web/";
//    public static String URL = "http://10.0.2.2:8081/Spot_MySQL_Web/";
    public static String temp = "192.168.196.152";
    public static String URL = "http://"+temp+":8081/AutoBike_Android_Server/";//10.0.2.2
    public static String URL_MemServlet = "http://"+temp+":8081/AutoBike_Android_Server/MemberServlet";
    public static String URL_SecOrdServlet = "http://"+temp+":8081/AutoBike_Android_Server/SecOrdServlet";
    public static String URL_RentOrdServlet = "http://"+temp+":8081/AutoBike_Android_Server/RentOrdServlet";
    public static String URL_MotorModelServlet = "http://"+temp+":8081/AutoBike_Android_Server/MotorModelServlet";
    public static String URL_MotorServlet = "http://"+temp+":8081/AutoBike_Android_Server/MotorServlet";
    public static String URL_LocationServlet = "http://"+temp+":8081/AutoBike_Android_Server/LocationServlet";
    public static String URL_EmtCateServlet = "http://"+temp+":8081/AutoBike_Android_Server/EmtCateServlet";
    public static String URL_NewsServlet = "http://"+temp+":8081/AutoBike_Android_Server/NewsServlet";
    public static String URL_MessageBoardServlet = "http://"+temp+":8081/AutoBike_Android_Server/MesBoardServlet";
    // check if the device connect to the network
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
