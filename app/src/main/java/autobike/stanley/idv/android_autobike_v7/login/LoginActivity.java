package autobike.stanley.idv.android_autobike_v7.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvlater = (TextView) findViewById(R.id.tvreg_later);
        tvlater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
}
