package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.RentOrder;
import autobike.stanley.idv.android_autobike_v7.tab.location.Location;
import autobike.stanley.idv.android_autobike_v7.tab.location.LocationGetAllTask;

public class Tab_RentBike_Detail_Confirm extends AppCompatActivity {

    private Bundle bundle;
    private ImageView ivRentConfirm;
    private TextView tvRentConfirmMotoType,tvRentConfirmTimeStart,tvRentConfirmTimeEnd,tvRentConfirmDays,tvRentConfirmGetLocation
                    ,tvRentConfirmBackLocation,tvRentConfirmHat,tvRentConfirmHand,tvRentConfirmShirt,tvRentConfirmV8,tvOrderTotalPrice
                    ,tvMotorPrice;
    private Button btnSendOrder;
    private Profile profile;
    private int totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_rent_bike_detail_confirm);
        bundle = getIntent().getExtras();
        profile = new Profile(this);
        findViews();
        ///set textViews
        try {
            setTextViews();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RentOrder rentOrder = new RentOrder();
                try {
                    rentOrder.setMemno(profile.getData("Memno"));
                    Motor motor = (Motor)(bundle.getSerializable("motor"));
                    rentOrder.setMotno(motor.getMotno());
                    String getLocation = bundle.getString("getlocation");
                    rentOrder.setSlocno(checkLocation(getLocation));
                    String backLocation = bundle.getString("backlocation");
                    rentOrder.setRlocno(checkLocation(backLocation));
                    String startDate = bundle.getString("Date");
                    String endDate = bundle.getString("endDate");
                    String Time = bundle.getString("Time");
                    String startFormat  = startDate + " " + Time + ":00";
                    String endFormat  = startDate + " " + Time + ":00";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    rentOrder.setMilstart(motor.getMile());
                    rentOrder.setTotal(totalPrice);
                    try {
                       rentOrder.setStartdate(new Timestamp(sdf.parse(startFormat).getTime()));
                       rentOrder.setEnddate(new Timestamp(sdf.parse(endFormat).getTime()));
                        bundle.putSerializable("rentorder",rentOrder);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        intent.setClass(Tab_RentBike_Detail_Confirm.this,Tab_RentBike_Pay.class);
                        startActivity(intent);

//                        new AddOrderTask().execute(Common.URL_RentOrdServlet,rentOrder,bundle.getString("hatitem")
//                        ,bundle.getString("handitem"),bundle.getString("shirtitem"),bundle.getString("v8item"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setTextViews() throws ExecutionException, InterruptedException {
        //set image
        String url = Common.URL + "MotorModelServlet";
        int imageSize = 300;

        String type = ((Motor)bundle.getSerializable("motor")).getModtype();
        new GetMotorModelImageByMotoType(ivRentConfirm).execute(url, type, imageSize);
        //set motorBrand
        MotorModel motorModel = new GetMotorModelVoByMotorTypeTask().execute(Common.URL_MotorModelServlet,type).get();
        tvRentConfirmMotoType.setText(motorModel.getBrand() + "\r\n" + motorModel.getName());
        //set date from
        tvRentConfirmTimeStart.setText(bundle.getString("Date") + "   " + bundle.getString("Time"));
        //set date end
        tvRentConfirmTimeEnd.setText(bundle.getString("endDate") + "   " + bundle.getString("Time"));
        //set rent days
        tvRentConfirmDays.setText(bundle.getString("Rentday"));
        //set get location
        tvRentConfirmGetLocation.setText(bundle.getString("getlocation"));
        //set back location
        tvRentConfirmBackLocation.setText(bundle.getString("backlocation"));
        //set hat prices
        tvRentConfirmHat.setText(bundle.getString("hatPrice"));
        //set hand prices
        tvRentConfirmHand.setText(bundle.getString("handPrice"));
        //set shirt prices
        tvRentConfirmShirt.setText(bundle.getString("shirtPrice"));
        //set v8 prices
        tvRentConfirmV8.setText(bundle.getString("v8Price"));
        //set motor rent price
        tvMotorPrice.setText(bundle.getString("motorRentTotalPrice"));
        //set total price
        totalPrice = Integer.parseInt(bundle.getString("hatPrice")) + Integer.parseInt(bundle.getString("handPrice"))
                    +Integer.parseInt(bundle.getString("shirtPrice"))+Integer.parseInt(bundle.getString("v8Price"))
                    +Integer.parseInt(bundle.getString("motorRentTotalPrice"));
        tvOrderTotalPrice.setText(String.valueOf(totalPrice));
    }

    private void findViews() {
        ivRentConfirm = (ImageView) findViewById(R.id.ivRentConfirm);
        tvRentConfirmMotoType = (TextView) findViewById(R.id.tvRentConfirmMotoType);
        tvRentConfirmTimeStart = (TextView) findViewById(R.id.tvRentConfirmTimeStart);
        tvRentConfirmTimeEnd = (TextView) findViewById(R.id.tvRentConfirmTimeEnd);
        tvRentConfirmDays = (TextView) findViewById(R.id.tvRentConfirmDays);
        tvRentConfirmGetLocation = (TextView) findViewById(R.id.tvRentConfirmGetLocation);
        tvRentConfirmBackLocation = (TextView) findViewById(R.id.tvRentConfirmBackLocation);
        tvRentConfirmHat = (TextView) findViewById(R.id.tvRentConfirmHat);
        tvRentConfirmHand = (TextView) findViewById(R.id.tvRentConfirmHand);
        tvRentConfirmShirt = (TextView) findViewById(R.id.tvRentConfirmShirt);
        tvRentConfirmV8 = (TextView) findViewById(R.id.tvRentConfirmV8);
        tvOrderTotalPrice = (TextView) findViewById(R.id.tvOrderTotalPrice);
        tvMotorPrice  = (TextView) findViewById(R.id.tvMotorPrice);
        btnSendOrder = (Button)findViewById(R.id.btnSendOrder);
    }

    private String checkLocation(String loc) throws ExecutionException, InterruptedException {
        List<Location> locationList = new LocationGetAllTask().execute(Common.URL_LocationServlet).get();
        for(Location location : locationList){
            if(location.getLocname().equals(loc)){
                return location.getLocno();
            }
        }
        return null;
    }
}
