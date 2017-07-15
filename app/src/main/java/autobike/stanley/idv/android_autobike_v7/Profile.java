package autobike.stanley.idv.android_autobike_v7;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;

import autobike.stanley.idv.android_autobike_v7.login.Member;

/**
 * Created by Stanley_NB on 2017/7/4.
 */

public class Profile {
    private SharedPreferences sharedPreferences;

    public Profile(Context context){
        sharedPreferences = context.getSharedPreferences("profile", Context.MODE_PRIVATE);
    }

    public void setData(String key,String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void clean(){
        sharedPreferences.edit().clear().commit();
    }

    public String getData(String key){
        return  sharedPreferences.getString(key,"");
    }

}
