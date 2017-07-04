package autobike.stanley.idv.android_autobike_v7.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.news.News;
import autobike.stanley.idv.android_autobike_v7.tab.news.NewsGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.news.Tab_News_Fragment;
import autobike.stanley.idv.android_autobike_v7.welcome.WelcomeActivity;

import static autobike.stanley.idv.android_autobike_v7.R.id.rvNews;

public class LoginNormalActivity extends AppCompatActivity {

    private static final String TAG = "LoginNormalActivity";
    private TextView tvAccount,tvPassword;
    private Button btCheckLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal);
        tvAccount = (TextView) findViewById(R.id.etAccount);
        tvPassword = (TextView) findViewById(R.id.etPassword);
        btCheckLogin = (Button) findViewById(R.id.btCheckLogin);
        btCheckLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = tvAccount.getText().toString();
                String password = tvAccount.getText().toString();
                if(account.equals("") || password.equals("")){
                    Toast.makeText(LoginNormalActivity.this,"Error",Toast.LENGTH_LONG).show();
                }else{
                    String checkResult = showCheckAccount();
                    if(checkResult.equals("allPass")){
                        Intent intent = new Intent();
                        intent.setClass(LoginNormalActivity.this, MainActivity.class);
                        LoginNormalActivity.this.startActivity(intent);
                        LoginNormalActivity.this.finish();
                    }else{

                    }
                }

            }
        });
    }

    private String showCheckAccount() {
        String jsonIn = null;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet";


            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                jsonIn = new LoginCheckTask().execute(url, tvAccount.getText().toString(), tvPassword.getText().toString()).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (jsonIn == null || jsonIn.isEmpty()) {
                Common.showToast(this, R.string.msg_NoMotorFound);
            } else {
                Toast.makeText(this, jsonIn, Toast.LENGTH_SHORT).show();
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoMotorFound);
        }

        return jsonIn;
    }
}
