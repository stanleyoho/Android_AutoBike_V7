package autobike.stanley.idv.android_autobike_v7.tab.sellBike;

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

import autobike.stanley.idv.android_autobike_v7.navigationlayout.RentOrder;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.SellOrder;

/**
 * Created by Stanley_NB on 2017/7/20.
 */

public class AddSecOrdTask extends AsyncTask<Object, Integer, Void> {
    private final static String TAG = "AddSecOrd";
    private final static String ACTION = "AddSecOrd";

    @Override
    protected Void doInBackground(Object... params) {
        String url = params[0].toString();
        SellOrder sellOrder = (SellOrder) params[1];
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("sellOrder", new Gson().toJson(sellOrder));

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

