package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMesGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessage;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessageGetImageTask;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_Fragment;

public class Navi_Rent_List extends Fragment {

    private static final String TAG = "RentListFragment";
    private RecyclerView rvRentRecycleView;
    private List<RentOrder> rentList;
    private Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.navi_rent_list, container, false);
        profile = new Profile(getActivity());
        rvRentRecycleView = (RecyclerView) view.findViewById(R.id.rvRentList);
        rvRentRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllRentList();
    }

    public void showAllRentList(){
        rentList = null;
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "RentOrdServlet";
            String account = profile.getData("fileaccount");
            String memno = profile.getData("fileid");
            List<RentOrder> rentOrdersList = null;
            try {
                rentList = new GetRentListTask().execute(url,memno).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (rentList == null || rentList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoSpotsFound);
            } else {
                rvRentRecycleView.setAdapter(new Navi_Rent_List.RentListRecyclerViewAdapter(getActivity(), rentList));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    private class RentListRecyclerViewAdapter extends RecyclerView.Adapter<Navi_Rent_List.RentListRecyclerViewAdapter.MyViewHolder>{

        private LayoutInflater layoutInflater;
        private List<RentOrder> rentOrdersList;

        public RentListRecyclerViewAdapter(Context context,List<RentOrder> rentOrdersList){
            layoutInflater = LayoutInflater.from(context);
            this.rentOrdersList = rentOrdersList;
        }

        @Override
        public RentListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.navi_rent_list_recycleitem, parent, false);
            return new Navi_Rent_List.RentListRecyclerViewAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(RentListRecyclerViewAdapter.MyViewHolder holder, int position) {
            RentOrder rentOrder = rentOrdersList.get(position);
            String url = Common.URL + "MotorModelServlet";
            String ordno = rentOrder.getRentno();
            int imageSize = 250;
            new GetMotorModelImageByOrdNo(holder.imageView).execute(url, ordno, imageSize);
            holder.tvRentNo.setText("訂單編號 :  " + rentOrder.getRentno());
            holder.tvRentCarNo.setText("車輛編號 :  " + rentOrder.getMotno());
            holder.tvRentLocStart.setText("取車地點 :  " + rentOrder.getSlocno());
            holder.tvRentLocBack.setText("還車地點 :  " + rentOrder.getRlocno());
            holder.tvRentStatus.setText("訂單狀態 :  " + rentOrder.getRlocno());
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvRentNo, tvRentCarNo,tvRentLocStart,tvRentLocBack,tvRentStatus;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.ivRentListImage);
                tvRentNo = (TextView) itemView.findViewById(R.id.tvRentListNo);
                tvRentCarNo = (TextView) itemView.findViewById(R.id.tvRentListCarNo);
                tvRentLocStart = (TextView) itemView.findViewById(R.id.tvRentListgetloc);
                tvRentLocBack = (TextView) itemView.findViewById(R.id.tvRentListbackloc);
                tvRentStatus = (TextView) itemView.findViewById(R.id.tvRentListstatus);
            }
        }

        @Override
        public int getItemCount() {
            return rentOrdersList.size();
        }
    }



}
