package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.Navi_Rent_List;

public class Tab_RentBike_Pay extends AppCompatActivity {

    private Profile profile;
    private ImageView ivPayImage;
    private Bundle bundle;
    private Button btnpaybutton;
    private EditText etcard1,etcard2,etcard3,etcard4,etcardsec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_rent_bike_pay);
        bundle = getIntent().getExtras();
        profile = new Profile(this);
        findViews();
        setTextListener();

        btnpaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(etcard1.getText().toString().trim()) || "".equals(etcard2.getText().toString().trim()) ||
                        "".equals(etcard3.getText().toString().trim()) || "".equals(etcard4.getText().toString().trim()) ||
                        "".equals(etcardsec.getText().toString().trim())){
                    Toast.makeText(Tab_RentBike_Pay.this,"please fill all the data",Toast.LENGTH_SHORT).show();
                }else{
                    new AddOrderTask().execute(Common.URL_RentOrdServlet,bundle.getSerializable("rentorder")
                            ,bundle.getString("hatitem"),bundle.getString("handitem")
                            ,bundle.getString("shirtitem"),bundle.getString("v8item"));
                    Intent intent = new Intent();
                    intent.setClass(Tab_RentBike_Pay.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void findViews() {
        ivPayImage = (ImageView)findViewById(R.id.ivPayImage);
        btnpaybutton = (Button)findViewById(R.id.btnpaybutton);
        etcard1 = (EditText) findViewById(R.id.etcard1);
        etcard2 = (EditText) findViewById(R.id.etcard2);
        etcard3 = (EditText) findViewById(R.id.etcard3);
        etcard4 = (EditText) findViewById(R.id.etcard4);
        etcardsec = (EditText) findViewById(R.id.etcardsec);
    }

    private void setTextListener() {
        ivPayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etcard1.setText("1111");
                etcard2.setText("1111");
                etcard3.setText("1111");
                etcard4.setText("1111");
                etcardsec.setText("111");
            }
        });
        etcard1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etcard1.getText().length() >=4){

                    etcard2.requestFocus();
                }
            }
        });
        etcard2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etcard2.getText().length() >=4){

                    etcard3.requestFocus();
                }
            }
        });
        etcard3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etcard3.getText().length() >=4){

                    etcard4.requestFocus();
                }
            }
        });
        etcard4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etcard4.getText().length() >=4){

                    etcardsec.requestFocus();
                }
            }
        });
        etcardsec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etcardsec.getText().length() >=3){
                    etcardsec.clearFocus();
                }
            }
        });
    }
}
