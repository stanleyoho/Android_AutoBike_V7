package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.Member;

public class Navi_Change_Member_Data extends AppCompatActivity {
    private EditText  etchangeMail,etchangeAddress,etchangePhone;
    private Button btnChangeConfirm;
    Member member = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_change_member_data);
        findViews();
        member = (Member) getIntent().getSerializableExtra("member");
        etchangeAddress.setText(member.getAddr());
        etchangeMail.setText(member.getMail());
        etchangePhone.setText(member.getPhone());
        etchangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangeAddress.setText("");
            }
        });
        etchangeMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangeMail.setText("");
            }
        });
        etchangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchangePhone.setText("");
            }
        });

        btnChangeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setAddr(etchangeAddress.getText().toString());
                member.setMail(etchangeMail.getText().toString());
                member.setPhone(etchangePhone.getText().toString());
                new UpdateMemberTask().execute(Common.URL_MemServlet,member);
                finish();
            }
        });
    }

    private void findViews() {
        etchangeAddress = (EditText) findViewById(R.id.etChangeAddress);
        etchangeMail = (EditText) findViewById(R.id.etChangeMail);
        etchangePhone = (EditText) findViewById(R.id.etChangePhone);
        btnChangeConfirm = (Button) findViewById(R.id.btn_memdatachangconfirm);
    }
}
