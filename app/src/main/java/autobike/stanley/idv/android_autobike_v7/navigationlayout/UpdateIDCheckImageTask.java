package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanley_NB on 2017/7/14.
 */

public class UpdateIDCheckImageTask extends AsyncTask<Object, Integer, Void> {
    private final static String TAG = "UpdateIDCheckImageTask";
    private final static String ACTION = "updateidcheckimage";

    @Override
    protected Void doInBackground(Object... params) {
        String url = params[0].toString();
//        String action = params[1].toString();
        String memno = params[1].toString();
//        String result;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("memno", memno);
        if (params[2] != null) {
            String imageBase64 = params[2].toString();
            jsonObject.addProperty("image1", imageBase64);
        }
        if (params[3] != null) {
            String imageBase64 = params[3].toString();
            jsonObject.addProperty("image2", imageBase64);
        }
        if (params[4] != null) {
            String imageBase64 = params[4].toString();
            jsonObject.addProperty("image3", imageBase64);
        }
        try {
            getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }

        return null;
    }

    private void getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(true); // allow inputs
        connection.setDoOutput(true); // allow outputs
        connection.setUseCaches(false); // do not use a cached copy
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        bw.write(jsonOut);
        Log.d(TAG, "jsonOut: " + jsonOut);
        bw.close();

        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } else {
            Log.d(TAG, "response code: " + responseCode);
        }
        connection.disconnect();
        Log.d(TAG, "jsonIn: " + sb);
//        return sb.toString();
    }
}