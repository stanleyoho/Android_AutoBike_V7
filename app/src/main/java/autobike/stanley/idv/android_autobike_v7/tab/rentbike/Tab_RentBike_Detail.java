package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.location.Location;
import autobike.stanley.idv.android_autobike_v7.tab.location.LocationGetAllTask;

public class Tab_RentBike_Detail extends AppCompatActivity {

    private ImageView ivimage,ivhandde,ivhandadd,ivhatde,ivhatadd,ivshirtde,ivshirtadd,ivv8de,ivv8add;
//    private EditText
    private Button btnSendConfirm;
    private TextView  tvRentTimeStart,tvRentTimeEnd,ethat,etshirt,ethand,etv8,tvRentDays,tvHatPrice,tvHandPrice,tvShirtPrice,tvV8Price,tvRentPrice;
    private Spinner getLocation,backLocation,payWay;
    private Bundle bundle;
    private String rentEndTime;
    private List<Location> locationList;
    private List<EmtCate> emtList;
    private MotorModel motorModel;
    private int hatPrice,handPrice,shirtPrice,v8Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_rent_bike_detail);
        findViews();
        setAdd_SubListener();
        //get bundle
        bundle = getIntent().getExtras();
        String type = ((Motor)bundle.getSerializable("sellmotor")).getModtype();

        //get location list
        try {
            locationList = new LocationGetAllTask().execute(Common.URL_LocationServlet).get();
            emtList = new GetEmtCateAllTask().execute(Common.URL_EmtCateServlet).get();
            motorModel = new GetMotorModelVoByMotorTypeTask().execute(Common.URL_MotorModelServlet,type).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //get price for emt
        for(EmtCate emtCate:emtList){
            if(emtCate.getEcno().equals("EC01")){
                hatPrice = emtCate.getPrice() ;
            }
            if(emtCate.getEcno().equals("EC02")){
                handPrice = emtCate.getPrice() ;
            }
            if(emtCate.getEcno().equals("EC03")){
                shirtPrice = emtCate.getPrice() ;
            }
            if(emtCate.getEcno().equals("EC04")){
                v8Price = emtCate.getPrice() ;
            }
        }
        //carete string array for spinner
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<locationList.size() ; i++){
            String locationName = locationList.get(i).getLocname();
            if(locationName.equals("總部")){

            }else{
                sb.append(locationName+",");
            }
        }
        String[] locationArray  = sb.toString().split(",");

        ArrayAdapter<String> adapterLocations = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationArray);
        adapterLocations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getLocation.setAdapter(adapterLocations);
        getLocation.setSelection(0,true);
        backLocation.setAdapter(adapterLocations);
        backLocation.setSelection(0,true);
        String[] tradeWayArray = {"信用卡","銀行轉帳"};
        ArrayAdapter<String> adapterTrade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tradeWayArray);
        adapterTrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payWay.setAdapter(adapterTrade);
        payWay.setSelection(0,true);
        //set image bitmap from byteArray
        String url = Common.URL + "MotorModelServlet";
        int imageSize = 300;
        new GetMotorModelImageByMotoType(ivimage).execute(url,type , imageSize);
        //set rentdays
        tvRentDays.setText(bundle.getString("Rentday"));
        int motorRentTotalPrice = Integer.parseInt(bundle.getString("Rentday")) * motorModel.getRenprice();
        tvRentPrice.setText(" * " + motorModel.getRenprice() + "  =  $" + motorRentTotalPrice);
        bundle.putString("motorRentTotalPrice",String.valueOf(motorRentTotalPrice));


        String rentFromData = bundle.getString("Date");
        String rentFromTime = bundle.getString("Time");
        int rentDays = Integer.parseInt(bundle.getString("Rentday"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date datetemp = sdf.parse(rentFromData);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(datetemp);
            calendar.add(Calendar.DATE,rentDays);
            rentEndTime = sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //set rent days
        tvRentTimeStart.setText(rentFromData +"   " +rentFromTime);
        tvRentTimeEnd.setText(rentEndTime +"   " +rentFromTime );
        bundle.putString("endDate",rentEndTime);
        btnSendConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("hatitem",ethat.getText().toString());
                bundle.putString("handitem",ethand.getText().toString());
                bundle.putString("shirtitem",etshirt.getText().toString());
                bundle.putString("v8item",etv8.getText().toString());
                bundle.putString("payWay",payWay.getSelectedItem().toString());
                bundle.putString("getlocation", getLocation.getSelectedItem().toString());
                bundle.putString("backlocation",backLocation.getSelectedItem().toString());
                bundle.putString("handPrice",String.valueOf(tvHandPrice.getText().toString()));
                bundle.putString("hatPrice",String.valueOf(tvHatPrice.getText().toString()));
                bundle.putString("shirtPrice",String.valueOf(tvShirtPrice.getText().toString()));
                bundle.putString("v8Price",String.valueOf(tvV8Price.getText().toString()));
                Intent intent = new Intent();
                intent.setClass(Tab_RentBike_Detail.this,Tab_RentBike_Detail_Confirm.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setAdd_SubListener() {
        //set hands
        ivhandadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(ethand.getText().toString());
                value = value + 1 ;
                if(value <= 0 ){
                    value = 0;
                }
                tvHandPrice.setText(String.valueOf(handPrice * value));
                ethand.setText(String.valueOf(value));
                bundle.putString("handPrice",String.valueOf(handPrice * value));
            }
        });
        ivhandde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(ethand.getText().toString());
                value = value - 1;
                if(value <= 0 ){
                    value = 0;
                }
                tvHandPrice.setText(String.valueOf(handPrice * value));
                ethand.setText(String.valueOf(value));
                bundle.putString("handPrice",String.valueOf(handPrice * value));
            }
        });
        //set hats
        ivhatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(ethat.getText().toString());
                value = value + 1 ;
                if(value <= 0 ){
                    value = 0;
                }
                tvHatPrice.setText(String.valueOf(hatPrice * value));
                ethat.setText(String.valueOf(value));
                bundle.putString("hatPrice",String.valueOf(hatPrice * value));
            }
        });
        ivhatde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(ethat.getText().toString());
                value = value - 1;
                if(value <= 0 ){
                    value = 0;
                }
                tvHatPrice.setText(String.valueOf(hatPrice * value));
                ethat.setText(String.valueOf(value));
                bundle.putString("hatPrice",String.valueOf(hatPrice * value));
            }
        });
        ivshirtadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(etshirt.getText().toString());
                value = value + 1 ;
                if(value <= 0 ){
                    value = 0;
                }
                tvShirtPrice.setText(String.valueOf(shirtPrice * value));
                etshirt.setText(String.valueOf(value));
                bundle.putString("shirtPrice",String.valueOf(shirtPrice * value));
            }
        });
        ivshirtde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(etshirt.getText().toString());
                value = value - 1;
                if(value <= 0 ){
                    value = 0;
                }
                tvShirtPrice.setText(String.valueOf(shirtPrice * value));
                etshirt.setText(String.valueOf(value));
                bundle.putString("shirtPrice",String.valueOf(shirtPrice * value));
            }
        });
        ivv8add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(etv8.getText().toString());
                value = value + 1 ;
                if(value <= 0 ){
                    value = 0;
                }
                tvV8Price.setText(String.valueOf(v8Price * value));
                etv8.setText(String.valueOf(value));
                bundle.putString("v8Price",String.valueOf(v8Price * value));
            }
        });
        ivv8de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(etv8.getText().toString());
                value = value - 1;
                if(value <= 0 ){
                    value = 0;
                }
                tvV8Price.setText(String.valueOf(v8Price * value));
                etv8.setText(String.valueOf(value));
                bundle.putString("v8Price",String.valueOf(v8Price * value));
            }
        });
    }

    private void findViews() {
        //imagesssssss
        ivimage = (ImageView) findViewById(R.id.ivRentCheck);
        ivhandadd = (ImageView) findViewById(R.id.ivhandadd);
        ivhandde = (ImageView) findViewById(R.id.ivhandde);
        ivhatadd = (ImageView) findViewById(R.id.ivhatadd);
        ivhatde = (ImageView) findViewById(R.id.ivhatde);
        ivshirtadd = (ImageView) findViewById(R.id.ivshirtadd);
        ivshirtde = (ImageView) findViewById(R.id.ivshirtde);
        ivv8add = (ImageView) findViewById(R.id.ivv8add);
        ivv8de = (ImageView) findViewById(R.id.ivv8de);
        //Button
        btnSendConfirm = (Button) findViewById(R.id.btnOrderConfirm);
        //TextView for Date
        tvRentTimeStart = (TextView) findViewById(R.id.tvRentTimeStart);
        tvRentTimeEnd = (TextView) findViewById(R.id.tvRentTimeEnd);
        tvRentDays = (TextView) findViewById(R.id.tvRentDays);
        //Textsssss
        tvRentPrice = (TextView) findViewById(R.id.tvRentPrice);
        ethat = (TextView) findViewById(R.id.ethat);
        etshirt = (TextView) findViewById(R.id.etshirt);
        ethand = (TextView) findViewById(R.id.ethand);
        etv8 = (TextView) findViewById(R.id.etv8);
        tvHatPrice = (TextView) findViewById(R.id.tvHatPrice);
        tvHandPrice = (TextView) findViewById(R.id.tvHandPrice);
        tvShirtPrice = (TextView) findViewById(R.id.tvShirtPrice);
        tvV8Price = (TextView) findViewById(R.id.tvV8Price);
        //Spinner
        getLocation = (Spinner) findViewById(R.id.spGetForm);
        backLocation = (Spinner) findViewById(R.id.spBackWhere);
        payWay = (Spinner) findViewById(R.id.spTradeWay);
    }
}
