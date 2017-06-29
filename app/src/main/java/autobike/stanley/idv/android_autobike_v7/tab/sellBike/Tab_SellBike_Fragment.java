package autobike.stanley.idv.android_autobike_v7.tab.sellBike;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Motor;

public class Tab_SellBike_Fragment extends Fragment {

    private final static String TAG = "SellBikeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvSellMotor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_sellbike_fragment, container, false);
        rvSellMotor = (RecyclerView) view.findViewById(R.id.rvSellMotor);
        rvSellMotor.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.sellBikeswipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllSellBike();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllSellBike();
    }

    private void showAllSellBike(){
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "MotorServlet";
            List<Motor> motorList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                motorList = new SellBikeGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (motorList == null || motorList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMotorFound);
            } else {
                rvSellMotor.setAdapter(new Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter(getActivity(), motorList));
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoMotorFound);
        }
    }

    private class SellMotorRecyclerViewAdapter extends RecyclerView.Adapter<Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Motor> motorList;
        private boolean[] motorExpanded;

        public SellMotorRecyclerViewAdapter(Context context, List<Motor> motorList) {
            layoutInflater = LayoutInflater.from(context);
            this.motorList = motorList;
            motorExpanded = new boolean[motorList.size()];
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView sellMotorTitle, sellMotorDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                sellMotorTitle = (TextView) itemView.findViewById(R.id.tvSellMotorTitle);
                sellMotorDetail = (TextView) itemView.findViewById(R.id.tvSellMotorDetail);
            }
        }

        @Override
        public int getItemCount() {
            return motorList.size();
        }

        @Override
        public Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.tab_sellbike_recycleitem, parent, false);
            return new Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter.ViewHolder viewHolder, int position) {
            Motor motor = motorList.get(position);
            viewHolder.sellMotorTitle.setText("機車型號 : " + motor.getModtype());
            viewHolder.sellMotorDetail.setText("車牌號碼: " + motor.getPlateno() + "\r\n" + "機車狀態: " + motor.getStatus() + "\r\n" + "機車里程數 : " + motor.getMile() +  "\r\n" + "機車所在地 : " +motor.getLocno());
//            viewHolder.motorNewsDetail.setVisibility(
//                    motorExpanded[position] ? View.VISIBLE : View.GONE);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    expand(viewHolder.getAdapterPosition());
//                }
//            });
        }

//        private void expand(int position) {
//             被點擊的資料列才會彈出內容，其他資料列的內容會自動縮起來
//             for (int i=0; i<newsExpanded.length; i++) {
//             newsExpanded[i] = false;
//             }
//             newsExpanded[position] = true;/////
//
//            motorExpanded[position] = !motorExpanded[position];
//            notifyDataSetChanged();
//        }
    }
}
