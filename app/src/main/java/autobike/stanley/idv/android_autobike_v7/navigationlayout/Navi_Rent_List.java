package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.login.LoginGetMemberVOByAccTask;
import autobike.stanley.idv.android_autobike_v7.login.Member;

public class Navi_Rent_List extends Fragment {

    private static final String TAG = "RentListFragment";
    private RecyclerView rvRentRecycleView;
    private List<RentOrder> rentList;
    private Profile profile;
    private Member member;

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
            String memno = profile.getData("Memno");

            List<RentOrder> rentOrdersList = null;
            try {
                rentList = new GetRentListTask().execute(Common.URL_RentOrdServlet,memno).get();
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
        RentOrder rentOrder;
        String motorBrand;

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
        public void onBindViewHolder(RentListRecyclerViewAdapter.MyViewHolder holder, final int position) {
            rentOrder = rentOrdersList.get(position);
            String url = Common.URL + "MotorModelServlet";
            String ordno = rentOrder.getRentno();
            int imageSize = 200;
            new GetMotorModelImageByRentNo(holder.imageView).execute(url, ordno, imageSize);
            holder.tvRentNo.setText(rentOrder.getRentno());
            url = Common.URL+"MotorServlet";
            String tempMotono = rentOrder.getMotno();
            try {
                motorBrand = new GetMotorTypeByMotoNo().execute(url,tempMotono).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            holder.tvRentCarNo.setText(motorBrand );
            holder.tvRentLocStart.setText(Common.checkLocation(rentOrder.getSlocno()));
            holder.tvRentLocBack.setText(Common.checkLocation(rentOrder.getRlocno()));
            holder.tvRentStatus.setText(Common.ordStaturCheck(rentOrder.getStatus()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RentOrder rentOrder = rentOrdersList.get(position);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(),Navi_Rent_List_Detail_Page.class);
                    Bundle bundle = new Bundle();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    bundle.putString("rentno",rentOrder.getRentno());
                    bundle.putString("getstart",rentOrder.getSlocno());
                    bundle.putString("getback",rentOrder.getRlocno());
                    bundle.putString("rentstatus",rentOrder.getStatus());
                    bundle.putString("motortype",motorBrand);
                    bundle.putString("startdate",sdf.format(rentOrder.getStartdate()));
                    bundle.putString("enddate",sdf.format(rentOrder.getEnddate()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

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
