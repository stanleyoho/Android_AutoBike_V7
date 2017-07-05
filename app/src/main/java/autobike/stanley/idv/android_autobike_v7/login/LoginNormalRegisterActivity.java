package autobike.stanley.idv.android_autobike_v7.login;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import autobike.stanley.idv.android_autobike_v7.R;

public class LoginNormalRegisterActivity extends AppCompatActivity {

    private EditText etRegisterAccount,etRegisterPassword,etPasswordRepeat,etRegisterID,etRegisterMail,etRegisterAddr;
    private TextView tvRegisterBirthdayResult;
    private ImageView ivRegisterBirthdayCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal_register);

        etRegisterAccount = (EditText)findViewById(R.id.etRegisterAccount);
        etRegisterPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etPasswordRepeat = (EditText)findViewById(R.id.etPasswordRepeat);
        etRegisterID = (EditText)findViewById(R.id.etRegisterID);
        etRegisterMail = (EditText)findViewById(R.id.etRegisterMail);
        etRegisterAddr = (EditText)findViewById(R.id.etRegisterAddr);
        tvRegisterBirthdayResult = (TextView)findViewById(R.id.tvRegisterBirthdayResult);
        ivRegisterBirthdayCalendar = (ImageView)findViewById(R.id.ivRegisterBirthdayCalendar);

        ivRegisterBirthdayCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(LoginNormalRegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        tvRegisterBirthdayResult.setText(format);
                        ivRegisterBirthdayCalendar.setVisibility(View.GONE);
                        tvRegisterBirthdayResult.setVisibility(View.VISIBLE);
                    }

                }, mYear,mMonth, mDay).show();
            }
        });
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }
}
