package autobike.stanley.idv.android_autobike_v7.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import autobike.stanley.idv.android_autobike_v7.R;

public class LoginNormalActivity extends AppCompatActivity {

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
                    Toast.makeText(LoginNormalActivity.this,"Pass",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
