package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;

public class Navi_Sell_List extends Fragment {

    private static final String TAG = "SellListFragment";
    private RecyclerView rvSellRecycleView;
    private List<SellOrder> sellList;
    private Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.navi_sell_list, container, false);
        profile = new Profile(getActivity());
        rvSellRecycleView = (RecyclerView) view.findViewById(R.id.rvSelltList);
        rvSellRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllSellList();
    }

    private void showAllSellList() {
        sellList = null;
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "SecOrdServlet";
            String account = profile.getData("fileaccount");
            String memno = profile.getData("fileid");

            try {
                sellList = new GetSellListTask().execute(url,memno).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (sellList == null || sellList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoSpotsFound);
            } else {
                rvSellRecycleView.setAdapter(new Navi_Sell_List.SellListRecyclerViewAdapter(getActivity(), sellList));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    private class SellListRecyclerViewAdapter extends RecyclerView.Adapter<Navi_Sell_List.SellListRecyclerViewAdapter.MyViewHolder>{

        private LayoutInflater layoutInflater;
        private List<SellOrder> sellOrdersList;

        public SellListRecyclerViewAdapter(Context context, List<SellOrder> sellOrdersList){
            layoutInflater = LayoutInflater.from(context);
            this.sellOrdersList = sellOrdersList;
        }

        @Override
        public Navi_Sell_List.SellListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.navi_sell_list_recycleitem, parent, false);
            return new Navi_Sell_List.SellListRecyclerViewAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(Navi_Sell_List.SellListRecyclerViewAdapter.MyViewHolder holder, int position) {
            SellOrder sellOrder = sellOrdersList.get(position);
            String url = Common.URL + "MotorModelServlet";
            String sellno = sellOrder.getSono();
            int imageSize = 250;
            new GetMotorModelImageBySellNo(holder.imageView).execute(url, sellno, imageSize);
            holder.tvSellNo.setText(sellOrder.getSono());
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvSellNo;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.ivSellListImage);
                tvSellNo = (TextView) itemView.findViewById(R.id.tvSellListNo);
            }
        }

        @Override
        public int getItemCount() {
            return sellOrdersList.size();
        }
    }


}
