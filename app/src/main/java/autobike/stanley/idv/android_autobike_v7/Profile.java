package autobike.stanley.idv.android_autobike_v7;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Stanley_NB on 2017/7/4.
 */

public class Profile {
    private SharedPreferences sharedPreferences;

    public Profile(Context context){
        sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
    }

    public void setMemVO(String mem_id){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mem_id", mem_id);
        editor.apply();
    }

    public String getMemId(){
        return  sharedPreferences.getString("mem_id","");
    }
}
