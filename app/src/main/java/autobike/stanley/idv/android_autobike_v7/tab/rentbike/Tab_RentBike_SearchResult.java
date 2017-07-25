package autobike.stanley.idv.android_autobike_v7.tab.rentbike;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;

public class Tab_RentBike_SearchResult extends AppCompatActivity {

    private final static String TAG = "MotorFragment";
    private RecyclerView rvMotor;//test
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_rent_bike_search_result);

        rvMotor = (RecyclerView) findViewById(R.id.rvMotor);
        rvMotor.setLayoutManager(new LinearLayoutManager(this));
        bundle = getIntent().getExtras();

        showAllRentBike();

    }

    private void showAllRentBike() {
        if (Common.networkConnected(this)) {
            String url = Common.URL + "MotorServlet";
            List<Motor> motorList = null;

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                if(bundle.getString("brand").equals("all")){//getIntent().getStringExtra("brand").equals("----")
                    motorList = new MotorGetAllTask().execute(url).get();
                }else{
                    motorList = new MotorGetAllWithBrandTask().execute(url,getIntent().getStringExtra("brand")).get();
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (motorList == null || motorList.isEmpty()) {
                Common.showToast(this, R.string.msg_NoMotorFound);
            } else {
                rvMotor.setAdapter(new Tab_RentBike_SearchResult.MotorRecyclerViewAdapter(this, motorList));
            }
            progressDialog.cancel();

        } else {
            Common.showToast(this, R.string.msg_NoMotorFound);
        }
    }

    private class MotorRecyclerViewAdapter extends RecyclerView.Adapter<Tab_RentBike_SearchResult.MotorRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<Motor> motorList;



        public MotorRecyclerViewAdapter(Context context, List<Motor> motorList) {
            layoutInflater = LayoutInflater.from(context);
            this.motorList = motorList;

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView motorNewsTitle, tvMotoNumber,tvMotostatus,tvMotorMils,tvMotosLocNow;
            ImageView ivimage;

            public ViewHolder(View itemView) {
                super(itemView);
                motorNewsTitle = (TextView) itemView.findViewById(R.id.tvMotoTitle);
                tvMotoNumber = (TextView) itemView.findViewById(R.id.tvMotoNumber);
                ivimage = (ImageView) itemView.findViewById(R.id.ivRentImage);
                tvMotostatus = (TextView)itemView.findViewById(R.id.tvMotostatus);
                tvMotorMils = (TextView)itemView.findViewById(R.id.tvMotorMils);
                tvMotosLocNow = (TextView)itemView.findViewById(R.id.tvMotosLocNow);
            }
        }

        @Override
        public int getItemCount() {
            return motorList.size();
        }

        @Override
        public Tab_RentBike_SearchResult.MotorRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.tab_rentbike_recycleitem, parent, false);
            return new Tab_RentBike_SearchResult.MotorRecyclerViewAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Tab_RentBike_SearchResult.MotorRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
            Motor motor = motorList.get(position);
            String url = Common.URL + "MotorModelServlet";
            String mototype = motor.getModtype();
            int imageSize = 200;
            new GetMotorModelImageByMotoType(viewHolder.ivimage).execute(url, mototype, imageSize);

            //get image & set bundle
            viewHolder.motorNewsTitle.setText(motor.getModtype());
            viewHolder.tvMotoNumber.setText(motor.getPlateno() );
            viewHolder.tvMotostatus.setText(Common.motorStaturCheck(motor.getStatus()));
            viewHolder.tvMotorMils.setText(String.valueOf(motor.getMile()));
            viewHolder.tvMotosLocNow.setText(Common.checkLocation(motor.getLocno()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("motor",motorList.get(position));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(Tab_RentBike_SearchResult.this,Tab_RentBike_Detail.class);
                    startActivity(intent);
                }
            });
        }
    }

    private byte[] changeImageViewToByte(ImageView ivimage){
        Bitmap bitmap =  ((BitmapDrawable) ivimage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
