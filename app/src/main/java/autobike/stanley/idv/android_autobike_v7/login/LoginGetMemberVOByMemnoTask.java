package autobike.stanley.idv.android_autobike_v7.login;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanley_NB on 2017/7/14.
 */

public class LoginGetMemberVOByMemnoTask extends AsyncTask<Object, Integer, Member> {

    private final static String TAG = "LoginGetMemberVOByAccTask";
    private final static String ACTION = "findbymemno";

    @Override
    protected Member doInBackground(Object... params) {
        String url = params[0].toString();
        String memno = params[1].toString();
        String jsonIn;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("memno", memno);
        try {
            jsonIn = getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e("log", e.toString());
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<Member>() { }.getType();
        return gson.fromJson(jsonIn, listType);

    }

    private String getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true); // allow inputs
        connection.setDoOutput(true); // allow outputs
        connection.setUseCaches(false); // do not use a cached copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d("log", "jsonOut: " + jsonOut);
        bw.close();

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                jsonIn.append(line);
            }
        } else {
            Log.d("log", "response code: " + responseCode);
        }
        connection.disconnect();
        Log.d("log", "jsonIn: " + jsonIn);
        return jsonIn.toString();
    }

}