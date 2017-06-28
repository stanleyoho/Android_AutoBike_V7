package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class Tab_RentBike_Fragment extends Fragment {

    private final static String TAG = "MotorFragment";
    private RecyclerView rvMotor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_rentbike_fragment, container, false);
        rvMotor = (RecyclerView) view.findViewById(R.id.rvMotor);
        rvMotor.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "MotorServlet";
            List<Motor> motorList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                motorList = new MotorGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (motorList == null || motorList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMotorFound);
            } else {
                rvMotor.setAdapter(new Tab_RentBike_Fragment.MotorRecyclerViewAdapter(getActivity(), motorList));
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoMotorFound);
        }
    }

    private class MotorRecyclerViewAdapter extends RecyclerView.Adapter<Tab_RentBike_Fragment.MotorRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Motor> motorList;
        private boolean[] motorExpanded;

        public MotorRecyclerViewAdapter(Context context, List<Motor> motorList) {
            layoutInflater = LayoutInflater.from(context);
            this.motorList = motorList;
            motorExpanded = new boolean[motorList.size()];
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView motorNewsTitle, motorNewsDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                motorNewsTitle = (TextView) itemView.findViewById(R.id.tvMotoTitle);
                motorNewsDetail = (TextView) itemView.findViewById(R.id.tvMotoDetail);
            }
        }

        @Override
        public int getItemCount() {
            return motorList.size();
        }

        @Override
        public Tab_RentBike_Fragment.MotorRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.tab_rentbike_recycleitem, parent, false);
            return new Tab_RentBike_Fragment.MotorRecyclerViewAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Tab_RentBike_Fragment.MotorRecyclerViewAdapter.ViewHolder viewHolder, int position) {
            Motor motor = motorList.get(position);
            viewHolder.motorNewsTitle.setText("機車型號 : " + motor.getModtype());
            viewHolder.motorNewsDetail.setText("車牌號碼: " + motor.getPlateno() + "\r\n" + "機車狀態: " + motor.getStatus() + "\r\n" + "機車里程數 : " + motor.getMile() +  "\r\n" + "機車所在地 : " +motor.getLocno());
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
