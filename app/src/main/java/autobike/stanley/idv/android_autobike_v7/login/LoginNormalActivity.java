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

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;

public class LoginNormalActivity extends AppCompatActivity {

    private static final String TAG = "LoginNormalActivity";
    private TextView tvAccount,tvPassword;
    private Button btCheckLogin;
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
                    Member member = getMemberVO();
                    if(member == null){
                        Toast.makeText(LoginNormalActivity.this, "AccountNotExit", Toast.LENGTH_LONG).show();
                    }else if (member.getAcc().equals(account) && member.getPwd().equals(password)) { //All pass
                        profile = new Profile(LoginNormalActivity.this);
                        profile.setData("Memno", member.getMemno());
                        profile.setData("Memacc", member.getAcc());
                        profile.setData("Memstatus", member.getStatus());
//                        profile.setData("filename", member.getMemname());
//                        profile.setData("filemail", member.getMail());
//                        profile.setData("filestatus", member.getStatus());
                        Intent intent = new Intent();
                        intent.setClass(LoginNormalActivity.this, MainActivity.class);
                        LoginNormalActivity.this.startActivity(intent);
                        LoginNormalActivity.this.finish();
                        Toast.makeText(LoginNormalActivity.this, "AllPass", Toast.LENGTH_LONG).show();
                    } else if (member.getAcc().equals(account) && !member.getPwd().equals(password)) { //Passowrd fial
                        Toast.makeText(LoginNormalActivity.this, "PasswordError", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });
    }

    private Member getMemberVO() {
        Member member = null;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet";
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                member = new LoginGetMemberVOByAccTask().execute(url, tvAccount.getText().toString()).get();
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
