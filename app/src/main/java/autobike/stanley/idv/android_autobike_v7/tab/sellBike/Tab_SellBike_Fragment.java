package autobike.stanley.idv.android_autobike_v7.tab.sellBike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.GetMotorModelImageByMotoType;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Motor;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Tab_RentBike_Detail;
import autobike.stanley.idv.android_autobike_v7.tab.rentbike.Tab_RentBike_SearchResult;

public class Tab_SellBike_Fragment extends Fragment {

    private final static String TAG = "SellBikeFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvSellMotor;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_sellbike_fragment, container, false);
        Log.d(TAG, "onCreateView: bundle:"+bundle);
        if(bundle == null){
            Log.d(TAG, "onCreateView: bundle is null");
        }
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
            ImageView ivImage;
            TextView sellMotorTitle, tvSellMotorNumber,tvsellMotorMils,tvsellMotosLocNow,tvsellMotostatus;

            public ViewHolder(View itemView) {
                super(itemView);
                ivImage = (ImageView)itemView.findViewById(R.id.ivSellMotorImage) ;
                sellMotorTitle = (TextView) itemView.findViewById(R.id.tvSellMotorTitle);
                tvSellMotorNumber = (TextView) itemView.findViewById(R.id.tvSellMotorNumber);
                tvsellMotorMils = (TextView) itemView.findViewById(R.id.tvsellMotorMils);
                tvsellMotosLocNow = (TextView) itemView.findViewById(R.id.tvsellMotosLocNow);
                tvsellMotostatus = (TextView) itemView.findViewById(R.id.tvsellMotostatus);
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
        public void onBindViewHolder(final Tab_SellBike_Fragment.SellMotorRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
             Motor motor = motorList.get(position);
            String url = Common.URL + "MotorModelServlet";
            String mototype = motor.getModtype();
            int imageSize = 200;
            new GetMotorModelImageByMotoType(viewHolder.ivImage).execute(url, mototype, imageSize);
            viewHolder.sellMotorTitle.setText(motor.getModtype());
            viewHolder.tvSellMotorNumber.setText(motor.getPlateno());
            viewHolder.tvsellMotorMils.setText(String.valueOf(motor.getMile()));
            viewHolder.tvsellMotosLocNow.setText(Common.checkLocation(motor.getLocno()));
            viewHolder.tvsellMotostatus.setText(Common.motorStaturCheck(motor.getStatus()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle = new Bundle();
                    bundle.putSerializable("sellmotor",motorList.get(position));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(getActivity(),Tab_SellBike_detail.class);
                    startActivity(intent);
                }
            });
        }
    }
}
