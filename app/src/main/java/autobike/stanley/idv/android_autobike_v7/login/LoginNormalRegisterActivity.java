package autobike.stanley.idv.android_autobike_v7.login;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;

public class LoginNormalRegisterActivity extends AppCompatActivity {

    private EditText etRegisterAccount,etRegisterPassword,etPasswordRepeat,etRegisterID,etRegisterMail,etRegisterAddr,etRegisterPhone;
    private TextView tvRegisterBirthdayResult;
    private ImageView ivRegisterBirthdayCalendar;
    private Button btnRegister;
    private RadioGroup rgSexGroup;
    private RadioButton rgSelected;
    private Member member;
    private Date result;
    private Profile profile;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal_register);
        profile = new Profile(this);
        findviews();
        ivRegisterBirthdayCalendar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
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
                        tvRegisterBirthdayResult.setText(format);}
                }, mYear,mMonth, mDay).show();
            }

        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regAccount = etRegisterAccount.getText().toString();
                String regPassword = etRegisterPassword.getText().toString();
                String regPasswordAgain = etPasswordRepeat.getText().toString();
                String regID = etRegisterID.getText().toString();
                String regMail = etRegisterMail.getText().toString();
                String regAdd = etRegisterAddr.getText().toString();
                String regPhone = etRegisterPhone.getText().toString();
                String regBirthday = tvRegisterBirthdayResult.getText().toString();
//                Toast.makeText(LoginNormalRegisterActivity.this,regBirthday,Toast.LENGTH_SHORT).show();
                String regSelectedText = rgSelected.getText().toString();

                if(regAccount.length() == 0 || regPassword.length() == 0 || regPasswordAgain.length() == 0 || regID.length() == 0 ||
                        regMail.length() == 0 || regAdd.length() == 0 || regPhone.length() == 0 || regBirthday.length() == 0 ){
                    Toast.makeText(LoginNormalRegisterActivity.this,"Do not Empty",Toast.LENGTH_SHORT).show();
                }else if(!  regPassword.equals(regPasswordAgain)){
                    Toast.makeText(LoginNormalRegisterActivity.this,"PW not the same",Toast.LENGTH_SHORT).show();
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        result =  sdf.parse(regBirthday);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.d("Date formate","error");
                    }
                    Long res = result.getTime();
                    member = new Member();
                    member.setAcc(regAccount);
                    member.setAddr(regAdd);
                    member.setBirth(new Timestamp(res));
                    member.setMail(regMail);
                    member.setMemname(regID);
                    member.setPhone(regPhone);
                    member.setPwd(regPassword);
                    member.setSex(regSelectedText);
                    String result = registerAccount();
                    String[] resultArray = result.split(",");
                    if(resultArray[0].equals("Insert_OK")){
                        Intent intent = new Intent();
                        profile.setData("Memacc",member.getAcc());
                        profile.setData("Memno",resultArray[1]);
                        intent.setClass(LoginNormalRegisterActivity.this, MainActivity.class);
                        LoginNormalRegisterActivity.this.startActivity(intent);
                        LoginNormalRegisterActivity.this.finish();
                    }else{
                        Toast.makeText(LoginNormalRegisterActivity.this,"inset error",Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }

    private void findviews() {
        etRegisterAccount = (EditText)findViewById(R.id.etRegisterAccount);
        etRegisterPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etPasswordRepeat = (EditText)findViewById(R.id.etPasswordRepeat);
        etRegisterID = (EditText)findViewById(R.id.etRegisterID);
        etRegisterMail = (EditText)findViewById(R.id.etRegisterMail);
        etRegisterAddr = (EditText)findViewById(R.id.etRegisterAddr);
        tvRegisterBirthdayResult = (TextView)findViewById(R.id.tvRegisterBirthdayResult);
        ivRegisterBirthdayCalendar = (ImageView)findViewById(R.id.ivRegisterBirthdayCalendar);
        btnRegister = (Button)findViewById(R.id.btRegisternormal);
        etRegisterPhone = (EditText)findViewById(R.id.etRegisterPhoneNumber);
        rgSexGroup = (RadioGroup)findViewById(R.id.rgSexGroup);
        rgSelected = (RadioButton)findViewById(rgSexGroup.getCheckedRadioButtonId());
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.format("%02d",monthOfYear + 1) + "-"
                + String.format("%02d",dayOfMonth);
    }

    private String registerAccount() {
        String jsonIn = null;
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MemberServlet";


            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                jsonIn = new LoginRegisterTask().execute(url,member).get();
                Log.e("jsonIn",jsonIn);
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
