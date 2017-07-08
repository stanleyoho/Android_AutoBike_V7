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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.navi_rent_list, container, false);
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
            Profile profile = new Profile(getActivity());
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
            holder.tvTitle.setText(rentOrder.getMotno());
            holder.tvDetail.setText(rentOrder.getRlocno());
        }
        class MyViewHolder extends RecyclerView.ViewHolder {
//            ImageView imageView;
            TextView tvTitle, tvDetail;

            public MyViewHolder(View itemView) {
                super(itemView);
//                imageView = (ImageView) itemView.findViewById(R.id.ivBoardMesImage);
                tvTitle = (TextView) itemView.findViewById(R.id.tvRentListTitle);
                tvDetail = (TextView) itemView.findViewById(R.id.tvRentListDetail);

            }
        }

        @Override
        public int getItemCount() {
            return rentOrdersList.size();
        }
    }



}
