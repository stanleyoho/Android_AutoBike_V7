package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import autobike.stanley.idv.android_autobike_v7.MainActivity;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.LoginNormalRegisterActivity;

public class Tab_RentBike_Fragment extends Fragment {

    ViewPager viewpager;
    ImageView ivCalendar,ivTime;
    TextView tvdateResult,tvtimeResult;
    Spinner spDays,spBrand,spCC;
    View view;
    Button  btnSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_rentbike_fragment, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] days = {"1", "2", "3", "4","5","6","7"};
        String[] brand = {"----","Kawasaki","YAMAHA","Benelli","KTM","SYM","KYMCO","AEON","SUZUKI"};
        btnSearch = (Button) view.findViewById(R.id.searchButton);
        spDays = (Spinner)view.findViewById(R.id.spDays);
        spDays.setSelection(0,true);
        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, days);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDays.setAdapter(adapterDays);
        spBrand = (Spinner)view.findViewById(R.id.spBrand);
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, brand);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBrand.setAdapter(adapterBrand);
        spBrand.setSelection(0,true);
        tvdateResult = (TextView)view.findViewById(R.id.tvdateresult);
        tvtimeResult = (TextView)view.findViewById(R.id.tvtimeresult);
        final Calendar c = Calendar.getInstance();
        int mYear, mMonth, mDay,mHour,mMinute;
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        tvdateResult.setText(setDateFormat(mYear,mMonth,mDay));
        tvtimeResult.setText(setTimeFormat(mHour,mMinute));
        ivCalendar = (ImageView)view.findViewById(R.id.ivcalendar);
        ivTime = (ImageView)view.findViewById(R.id.ivtime);
        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear, mMonth, mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year,month,day);
                        tvdateResult.setText(format);}
                }, mYear,mMonth, mDay).show();
            }

        });

        ivTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mHour,mMinute;
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        String timeFormat = setTimeFormat(mHour,mMinute);
                        tvtimeResult.setText(timeFormat);
                    }
                }, mHour,mMinute,false).show();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brand = spBrand.getSelectedItem().toString();
                Bundle bundle = new Bundle();
                bundle.putString("brand",brand);
                bundle.putString("Rentday",spDays.getSelectedItem().toString());
                bundle.putString("Date",tvdateResult.getText().toString());
                bundle.putString("Time",tvtimeResult.getText().toString());
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(getActivity(),Tab_RentBike_SearchResult.class);
                startActivity(intent);
            }
        });
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
