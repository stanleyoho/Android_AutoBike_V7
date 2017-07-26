package autobike.stanley.idv.android_autobike_v7;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Common {

//    public static String URL = "http://192.168.196.189:8080/Spot_MySQL_Web/";
//    public static String URL = "http://10.0.2.2:8081/Spot_MySQL_Web/";
    public static String temp = "192.168.43.99";
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

    public static String checkLocation(String input){
        if(input.equals("TPE00")){
            return "總部";
        }else if(input.equals("TPE01")){
            return "台北車站";
        } else if(input.equals("NTC01")){
            return "板橋車站";
        } else if(input.equals("TXG01")){
            return "臺中車站";
        } else if(input.equals("TNN01")){
            return "台南車站";
        } else if(input.equals("KHH01")){
            return "高雄車站";
        }else{
            return "Location Check Error";
        }
    }

    public static String idStaturCheck(String input){
        if(input.equals("uncompleted")){
            return "簡易註冊";
        }else if(input.equals("unconfirmed")){
            return "尚未認證";
        } else if(input.equals("verifing")){
            return "等待認證";
        } else if(input.equals("confirmed")){
            return "認證合格";
        }else{
            return "Status Check Error";
        }
    }

    public static String ordStaturCheck(String input){
        if(input.equals("unpaid")){
            return "待繳費";
        }else if(input.equals("unoccupied")){
            return "未交車";
        } else if(input.equals("noshow")){
            return "逾期未交車";
        } else if(input.equals("noreturn")){
            return "未還車";
        }else if(input.equals("canceled")){
            return "訂單取消";
        }else if(input.equals("closed")){
            return "正常結案";
        }else if(input.equals("other")){
            return "其他";
        }else if(input.equals("abnormalclosed")){
            return "異常結案";
        }else if(input.equals("paid")){
            return "已付款";
        }else if(input.equals("overtime")){
            return "逾期未還";
        }else if(input.equals("available")){
            return "可找到";
        }else{
            return "Order Status Check Error";
        }
    }

    public static String motorStaturCheck(String input){
        if(input.equals("unleasable")){
            return "不可租";
        }else if(input.equals("leasable")){
            return "可出租";
        } else if(input.equals("reserved")){
            return "已預約";
        } else if(input.equals("occupied")){
            return "出租中";
        }else if(input.equals("dispatching")){
            return "調度中";
        }else if(input.equals("seconsale")){
            return "二手上架中";
        }else if(input.equals("secreserved")){
            return "二手已預約";
        }else if(input.equals("secpause")){
            return "二手未上架";
        }else if(input.equals("secsaled")){
            return "二手已售出";
        }else if(input.equals("other")){
            return "其他";
        }else{
            return "Motor Status Check Error";
        }
    }

}
