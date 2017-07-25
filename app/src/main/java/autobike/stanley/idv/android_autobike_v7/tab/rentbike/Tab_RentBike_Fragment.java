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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.LoginNormalRegisterActivity;

public class Tab_RentBike_Fragment extends Fragment {

    private ViewPager viewpager;
    private ImageView ivCalendar,ivTime,ivvpImage;
    private TextView tvdateResult,tvtimeResult;
    private Spinner spDays,spBrand,spCC;
    private View view;
    private Button  btnSearch;
    private Profile profile;
    private List<Integer> vpList;
    private ViewPager vppager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_rentbike_fragment, container, false);
        vpList = new ArrayList<Integer>();
        vpList.add(R.drawable.mm101);
        vpList.add(R.drawable.mm102);
        vpList.add(R.drawable.mm103);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), vpList);
        ivvpImage = (ImageView) view.findViewById(R.id.ivvpImage);
        vppager = (ViewPager)view.findViewById(R.id.vppager);
        vppager.setAdapter(viewPagerAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(),2000,3000);
        String[] days = {"1", "2", "3", "4","5","6","7"};
        String[] brand = {"all","Kawasaki","YAMAHA","Benelli","KTM","SYM","KYMCO","AEON","SUZUKI"};
        profile = new Profile(getActivity());
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
                if(profile.getData("Memstatus").equals("confirmed")){
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(),Tab_RentBike_SearchResult.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"必須做身分驗證才能租車",Toast.LENGTH_SHORT).show();
                }

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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        List<Integer> vpberList;

        private ViewPagerAdapter(FragmentManager fm, List<Integer> memberList) {
            super(fm);
            this.vpberList = memberList;
        }

        @Override
        public int getCount() {
            return vpberList.size();
        }

        @Override
        public Fragment getItem(int position) {
            int temp = vpList.get(position);
            tab_rentbike_viewpager vpfragment = new tab_rentbike_viewpager();
            Bundle args = new Bundle();
            args.putInt("vp", temp);
            vpfragment.setArguments(args);
            return vpfragment;
        }
    }

    public class MyTimeTask extends TimerTask{
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(vppager.getCurrentItem()==0){
                        vppager.setCurrentItem(1);
                    }
                    else if(vppager.getCurrentItem()==(1)){
                        vppager.setCurrentItem(2);
                    }
                    else vppager.setCurrentItem(0);
                }
            });
        }
    }
}
