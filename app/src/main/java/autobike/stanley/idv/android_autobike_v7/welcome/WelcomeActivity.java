package autobike.stanley.idv.android_autobike_v7.welcome;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.sendMessageDelayed(new Message(),1000);
    }
    private Handler handler = new Handler(){

        public void handleMessage(Message msg) {  // 使用msg分發到主程式
            super.handleMessage(msg);
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, LoginActivity.class); //換頁到page2頁面
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }

    };
}
