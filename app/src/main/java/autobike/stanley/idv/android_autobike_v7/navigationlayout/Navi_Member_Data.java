package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.Member;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMesGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessage;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_Fragment;

public class Navi_Member_Data extends Fragment {

    private static final String TAG = "Navi_mem_Data";
    private Member member;
    private Fragment fbody;
    private View view;
    private TextView memid,memAccount,memdataMail,memdataAddr,memdataSex,memdataPhone,memdataBirth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.navi_member_data, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        findViews();
        getMemData();
        memid.setText("姓名 : " +member.getMemname());
        memAccount.setText("帳號 : " +member.getAcc());
        memdataMail.setText("信箱 : " +member.getMail());
        memdataAddr.setText("地址 : " +member.getAddr());
        memdataSex.setText("性別 : " + member.getSex());
        memdataPhone.setText("電話 : " + member.getPhone());
        memdataBirth.setText("生日 : " + new SimpleDateFormat("yyyy-MM-dd").format( member.getBirth()));
    }

    private void findViews() {
        memid =(TextView) (getView().findViewById(R.id.memid));
        memAccount =(TextView) (getView().findViewById(R.id.memAccount));
        memdataMail =(TextView) (getView().findViewById(R.id.memdataMail));
        memdataAddr =(TextView) (getView().findViewById(R.id.memdataAddr));
        memdataSex =(TextView) (getView().findViewById(R.id.memdataSex));
        memdataPhone =(TextView) (getView().findViewById(R.id.memdataPhone));
        memdataBirth =(TextView) (getView().findViewById(R.id.memdataBirth));

    }

    private void getMemData() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "MemberServlet";
            Profile profile = new Profile(getActivity());
            String memdata = profile.getData("Account");
            Log.d("getMenber",memdata);
            try {
                member = new GetMemTask().execute(url,memdata).get();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (member == null) {
                Common.showToast(getActivity(), R.string.msg_NoSpotsFound);
            } else {

                Toast.makeText(getActivity(),member.getAcc(),Toast.LENGTH_SHORT).show();
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }
}
