package autobike.stanley.idv.android_autobike_v7.tab.sellBike;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.navigationlayout.SellOrder;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.GetMotorModelImageByMotoType;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.GetMotorModelVoByMotorTypeTask;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Motor;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.MotorModel;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Tab_RentBike_Detail;

public class Tab_SellBike_detail extends AppCompatActivity {

    private Button btnsendsecord;
    private ImageView ivsellcalendar,ivselltime,ivSellDetailPageImage;
    private TextView tvselldate,tvselltime,tvsellprice,tvsellmills,tvselltype;
    private Spinner spsellpayway;
    private Bundle bundle;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_sell_bike_detail);
        bundle = getIntent().getExtras();
        profile = new Profile(this);
        findViews();
        setTests();
        //datepicker
        ivsellcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(Tab_SellBike_detail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        tvselldate.setText(format);}
                }, mYear,mMonth, mDay).show();
            }
        });
        //timepicker
        ivselltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mHour,mMinute;
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                new TimePickerDialog(Tab_SellBike_detail.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        String timeFormat = setTimeFormat(mHour,mMinute);
                        tvselltime.setText(timeFormat);
                    }
                }, mHour,mMinute,false).show();
            }
        });
        btnsendsecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellOrder sellOrder = new SellOrder();
                sellOrder.setMemno(profile.getData("Memno"));
                sellOrder.setMotorno(((Motor)bundle.getSerializable("sellmotor")).getMotno());
                new AddSecOrdTask().execute(Common.URL_SecOrdServlet,sellOrder);
            }
        });
    }

    private void setTests(){
        //set motor typr
        String type = ((Motor)bundle.getSerializable("sellmotor")).getModtype();
        MotorModel motorModel = null;
        try {
            motorModel = new GetMotorModelVoByMotorTypeTask().execute(Common.URL_MotorModelServlet,type).get();
            tvselltype.setText(motorModel.getBrand() + " , " + motorModel.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //set motor mills
        tvsellmills.setText(((Motor)bundle.getSerializable("sellmotor")).getMile().toString());
        //set motor price
        tvsellprice.setText(motorModel.getSaleprice().toString());
        //set payway spinner
        String[] tradeWayArray = {"信用卡","銀行轉帳"};
        ArrayAdapter<String> adapterTrade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tradeWayArray);
        adapterTrade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spsellpayway.setAdapter(adapterTrade);
        spsellpayway.setSelection(0,true);
        //set image
        int imageSize = 300;
        new GetMotorModelImageByMotoType(ivSellDetailPageImage).execute(Common.URL_MotorModelServlet, type, imageSize);
    }

    private void findViews() {
        ivSellDetailPageImage = (ImageView) findViewById(R.id.ivSellDetailPageImage);
        ivsellcalendar = (ImageView) findViewById(R.id.ivsellcalendar);
        ivselltime = (ImageView) findViewById(R.id.ivselltime);
        tvselldate = (TextView) findViewById(R.id.tvselldate);
        tvselltime = (TextView) findViewById(R.id.tvselltime);
        tvsellprice = (TextView) findViewById(R.id.tvsellprice);
        tvsellmills = (TextView) findViewById(R.id.tvsellmills);
        btnsendsecord = (Button) findViewById(R.id.btnsendsecord);
        spsellpayway = (Spinner) findViewById(R.id.spsellpayway);
        tvselltype = (TextView)findViewById(R.id.tvselltype);
    }

    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.format("%02d",monthOfYear + 1) + "-"
                + String.format("%02d",dayOfMonth);
    }

    private String setTimeFormat(int hour,int minute){
        return String.format("%02d",hour) + ":" + String.format("%02d",minute);
    }
}
