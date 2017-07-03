package autobike.stanley.idv.android_autobike_v7.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.R;

public class LoginActivity extends AppCompatActivity {

    private TextView tvlater,tvNormalLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvlater = (TextView) findViewById(R.id.tvreg_later);
        tvNormalLogin = (TextView) findViewById(R.id.tvNormalLogin);
        tvlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        tvNormalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,LoginNormalActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

}
