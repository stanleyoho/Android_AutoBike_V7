package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autobike.stanley.idv.android_autobike_v7.R;

public class Navi_Member_Data extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.navi_member_data, container, false);
        return view;
    }
}
