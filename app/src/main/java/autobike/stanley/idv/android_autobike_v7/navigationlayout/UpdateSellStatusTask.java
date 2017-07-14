package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stanley_NB on 2017/7/14.
 */

public class UpdateSellStatusTask extends AsyncTask<Object, Integer, Void> {
    private final static String TAG = "UpdateSellStatusTask";
    private final static String ACTION = "updatebystatus";

    @Override
    protected Void doInBackground(Object... params) {
        String url = params[0].toString();
        String sellno = params[1].toString();
        String status = params[2].toString();
//        String jsonIn;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", ACTION);
        jsonObject.addProperty("sellno", sellno);
        jsonObject.addProperty("status", status);
        try {
            getRemoteData(url, jsonObject.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
//        Gson gson = new Gson();
//        Type memType = new TypeToken<Member>() { }.getType();
        return null;
    }

    private void getRemoteData(String url, String jsonOut) throws IOException {
        StringBuilder jsonIn = new StringBuilder();
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
            Log.d(TAG, "Success " );
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                jsonIn.append(line);
//            }
        } else {
            Log.d(TAG, "response code: " + responseCode);
        }
        connection.disconnect();
//        Log.d(TAG, "jsonIn: " + jsonIn);
//        return jsonIn.toString();
    }
}
