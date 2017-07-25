package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import autobike.stanley.idv.android_autobike_v7.R;

/**
 * Created by Stanley_NB on 2017/7/25.
 */

public class tab_rentbike_viewpager extends Fragment {

    private int temp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temp = getArguments().getInt("vp");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_rentbike_viewpager, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.ivvpImage);
        ivImage.setImageResource(temp);
        return view;
    }

}
