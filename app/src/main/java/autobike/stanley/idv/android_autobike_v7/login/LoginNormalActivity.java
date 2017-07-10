package autobike.stanley.idv.android_autobike_v7.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.GetMemTask;
import autobike.stanley.idv.android_autobike_v7.tab.news.News;
import autobike.stanley.idv.android_autobike_v7.tab.news.NewsGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.news.Tab_News_Fragment;
import autobike.stanley.idv.android_autobike_v7.welcome.WelcomeActivity;

import static autobike.stanley.idv.android_autobike_v7.R.id.rvNews;

public class LoginNormalActivity extends AppCompatActivity {

    private static final String TAG = "LoginNormalActivity";
    private TextView tvAccount,tvPassword;
    private Button btCheckLogin;
    private Member member;
    private Profile profile;

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
                String password = tvPassword.getText().toString();

                if (account.equals("") || password.equals("")){
                    Toast.makeText(LoginNormalActivity.this,"Please insert id & password",Toast.LENGTH_LONG).show();
                }else {
                    member = showCheckAccount();
                    if(member == null){
                        Toast.makeText(LoginNormalActivity.this, "AccountNotExit", Toast.LENGTH_LONG).show();
                    }else if (member.getAcc().equals(account) && member.getPwd().equals(password)) {
                        profile = new Profile(LoginNormalActivity.this);
                        profile.setData("filememno", member.getMemno());
                        profile.setData("fileaccount", member.getAcc());
                        profile.setData("filename", member.getMemname());
                        profile.setData("filemail", member.getMail());
                        profile.setData("filestatus", member.getStatus());
                        Intent intent = new Intent();
                        intent.setClass(LoginNormalActivity.this, MainActivity.class);
                        LoginNormalActivity.this.startActivity(intent);
                        LoginNormalActivity.this.finish();
                        Toast.makeText(LoginNormalActivity.this, "AllPass", Toast.LENGTH_LONG).show();
                    } else if (member.getAcc().equals(account) && !member.getPwd().equals(password)) {
                        Toast.makeText(LoginNormalActivity.this, "PasswordError", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

    private Member showCheckAccount() {
        Member member = null;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet";


            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                member = new GetMemTask().execute(url, tvAccount.getText().toString()).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (member == null ) {
                Common.showToast(this, R.string.msg_NoMotorFound);
            } else {
                Toast.makeText(this, "Got MEMBER VO", Toast.LENGTH_SHORT).show();
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoMotorFound);
        }

        return member;
    }
}
