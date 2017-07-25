package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.LoginGetMemberVOByAccTask;
import autobike.stanley.idv.android_autobike_v7.login.Member;

public class Navi_Member_Data extends Fragment {

    private static final String TAG = "Navi_mem_Data";
    private Member member;
    private Fragment fbody;
    private View view;
    private TextView memid,memAccount,memdataMail,memdataAddr,memdataSex,memdataPhone,memdataBirth,memdataStatus;
    private Button btnChange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.navi_member_data, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        findViews();
        Profile profile = new Profile(getActivity());
        String tempMemacc = profile.getData("Memacc");
        Log.e("Memacc",tempMemacc);
        String tempUrl = Common.URL + "MemberServlet";
        try {
            member = new LoginGetMemberVOByAccTask().execute(tempUrl,tempMemacc).get();
            memid.setText(member.getMemname());
            memAccount.setText(member.getAcc());
            memdataMail.setText(member.getMail());
            memdataAddr.setText(member.getAddr());
            memdataSex.setText(member.getSex());
            memdataPhone.setText(member.getPhone());
            memdataBirth.setText(new SimpleDateFormat("yyyy-MM-dd").format( member.getBirth()));
            memdataStatus.setText(Common.idStaturCheck(member.getStatus()));

            profile.setData("Memstatus",member.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),Navi_Change_Member_Data.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("member", member);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        memid =(TextView) (getView().findViewById(R.id.memid));
        memAccount =(TextView) (getView().findViewById(R.id.memAccount));
        memdataMail =(TextView) (getView().findViewById(R.id.memdataMail));
        memdataAddr =(TextView) (getView().findViewById(R.id.memdataAddr));
        memdataSex =(TextView) (getView().findViewById(R.id.memdataSex));
        memdataPhone =(TextView) (getView().findViewById(R.id.memdataPhone));
        memdataBirth =(TextView) (getView().findViewById(R.id.memdataBirth));
        btnChange = (Button) (getView().findViewById(R.id.btn_changeMemData));
        memdataStatus = (TextView) (getView().findViewById(R.id.memdataStatus));
    }

//    private Member getMemData() {
//        if (Common.networkConnected(getActivity())) {
//            String url = Common.URL + "MemberServlet";
//            Profile profile = new Profile(getActivity());
//            String memacc = profile.getData("filememacc");
//            String jsonIn;
//            try {
//                jsonIn = new LoginGetMemberVOByAccTask().execute(url,memacc).get();
//
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
//            if (member == null) {
//                Common.showToast(getActivity(), R.string.msg_NoSpotsFound);
//            } else {
//
//                Toast.makeText(getActivity(),member.getAcc(),Toast.LENGTH_SHORT).show();
//            }
//            Gson gson = new Gson();
//            Type listType = new TypeToken<Member>() { }.getType();
//            return gson.fromJson(jsonIn, listType);
//        } else {
//            Common.showToast(getActivity(), R.string.msg_NoNetwork);
//        }
//    }
}
