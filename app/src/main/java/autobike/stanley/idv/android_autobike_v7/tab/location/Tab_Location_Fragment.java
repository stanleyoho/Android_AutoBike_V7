package autobike.stanley.idv.android_autobike_v7.tab.location;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import autobike.stanley.idv.android_autobike_v7.R;

public class Tab_Location_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //導入Tab分頁的Fragment Layout
        return inflater.inflate(R.layout.tab_location_showmap, container, false);

    }
}
