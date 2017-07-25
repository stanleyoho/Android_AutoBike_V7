package autobike.stanley.idv.android_autobike_v7.tab.sellBike;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import autobike.stanley.idv.android_autobike_v7.navigationlayout.SellOrder;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.AddOrderTask;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Motor;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Tab_RentBike_Pay;

public class tab_sell_bike_pay extends AppCompatActivity {

    private Profile profile;
    private ImageView ivsellPayImage;
    private Bundle bundle;
    private Button btnsellpaybutton;
    private EditText etsellcard1,etsellcard2,etsellcard3,etsellcard4,etsellcardsec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_sell_bike_pay);
        bundle = getIntent().getExtras();
        profile = new Profile(this);
        findViews();
        setTextListener();
        btnsellpaybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(etsellcard1.getText().toString().trim()) || "".equals(etsellcard2.getText().toString().trim()) ||
                        "".equals(etsellcard3.getText().toString().trim()) || "".equals(etsellcard4.getText().toString().trim()) ||
                        "".equals(etsellcardsec.getText().toString().trim())){
                    Toast.makeText(tab_sell_bike_pay.this,"please fill all the data",Toast.LENGTH_SHORT).show();
                }else{
                    final AlertDialog build=
                            new AlertDialog.Builder(tab_sell_bike_pay.this)
                                    .setIcon(R.drawable.ic_alert)
                                    .setTitle("Really?")
                                    .setMessage("確定付款?")
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SellOrder sellOrder = new SellOrder();
                                            sellOrder.setMemno(profile.getData("Memno"));
                                            sellOrder.setMotorno(((Motor)bundle.getSerializable("sellmotor")).getMotno());
                                            new AddSecOrdTask().execute(Common.URL_SecOrdServlet,sellOrder);
                                            //clean previous activity
                                            Intent intent = new Intent();
                                            intent.setClass(tab_sell_bike_pay.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).create();
                    build.show();

                }

            }
        });

    }

    private void setTextListener() {
        ivsellPayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etsellcard1.setText("1111");
                etsellcard2.setText("1111");
                etsellcard3.setText("1111");
                etsellcard4.setText("1111");
                etsellcardsec.setText("111");
            }
        });
        etsellcard1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etsellcard1.getText().length() >=4){

                    etsellcard2.requestFocus();
                }
            }
        });
        etsellcard2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etsellcard2.getText().length() >=4){

                    etsellcard3.requestFocus();
                }
            }
        });
        etsellcard3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etsellcard3.getText().length() >=4){

                    etsellcard4.requestFocus();
                }
            }
        });
        etsellcard4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etsellcard4.getText().length() >=4){

                    etsellcardsec.requestFocus();
                }
            }
        });
        etsellcardsec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(etsellcardsec.getText().length() >=3){
                    etsellcardsec.clearFocus();
                }
            }
        });
    }

    private void findViews() {
        ivsellPayImage = (ImageView)findViewById(R.id.ivsellPayImage);
        btnsellpaybutton = (Button)findViewById(R.id.btnsellpaybutton);
        etsellcard1 = (EditText) findViewById(R.id.etsellcard1);
        etsellcard2 = (EditText) findViewById(R.id.etsellcard2);
        etsellcard3 = (EditText) findViewById(R.id.etsellcard3);
        etsellcard4 = (EditText) findViewById(R.id.etsellcard4);
        etsellcardsec = (EditText) findViewById(R.id.etsellcardsec);
    }
}
