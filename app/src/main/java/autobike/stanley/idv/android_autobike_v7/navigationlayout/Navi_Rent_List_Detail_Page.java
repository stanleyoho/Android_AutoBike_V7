package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;

public class Navi_Rent_List_Detail_Page extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvrentno,motortype,rentstart,rentback,rentloc,backloc,rentstatus;
    private Button btnCancel;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        String url = Common.URL + "MotorModelServlet";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_rent_list_detail_page);
        findViews();
        bundle =this.getIntent().getExtras();
        new GetMotorModelImageByRentNo(imageView).execute(Common.URL_MotorModelServlet,bundle.getString("rentno"),300);
        tvrentno.setText(bundle.getString("rentno"));
        motortype.setText(bundle.getString("motortype"));
        rentstart.setText(bundle.getString("startdate"));
        rentback.setText(bundle.getString("enddate"));
        rentloc.setText(Common.checkLocation(bundle.getString("getstart")));
        backloc.setText(Common.checkLocation(bundle.getString("getback")));
        rentstatus.setText(Common.ordStaturCheck(bundle.getString("rentstatus")));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rentstatus.getText().toString().equals("訂單取消") || rentstatus.getText().toString().equals("正常結案")){

                    Toast.makeText(Navi_Rent_List_Detail_Page.this,"此訂單已取消",Toast.LENGTH_SHORT).show();
                }else{

                    final AlertDialog build=
                    new AlertDialog.Builder(Navi_Rent_List_Detail_Page.this)
                            .setIcon(R.drawable.ic_alert)
                            .setTitle("Really?")
                            .setMessage("確定要取消訂單嗎?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String url = Common.URL + "RentOrdServlet";
                                    try {
                                        new UpdateRentStatusTask().execute(url,bundle.getString("rentno"),"canceled").get();
                                        RentOrder rentordvo = (RentOrder) new GetRentVOByRentNoTask().execute(url,bundle.getString("rentno")).get();
                                        rentstatus.setText(Common.ordStaturCheck(rentordvo.getStatus()));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).create();
                    build.show();

                }

            }
        });
    }

    private void findViews() {
        imageView = (ImageView) findViewById(R.id.ivRentDetailImage);
        tvrentno = (TextView) findViewById(R.id.tvRentDetailRentNo);
        motortype = (TextView) findViewById(R.id.tvRentDetailMotorType);
        rentstart = (TextView) findViewById(R.id.tvRentDetailStartDate);
        rentback = (TextView) findViewById(R.id.tvRentDetailEndDate);
        rentloc = (TextView) findViewById(R.id.tvRentDetailGetLocation);
        backloc = (TextView) findViewById(R.id.tvRentDetailBackLocation);
        rentstatus = (TextView) findViewById(R.id.tvRentDetailPayStatus);
        btnCancel = (Button) findViewById(R.id.btnRentDetailCancel);
    }

}
