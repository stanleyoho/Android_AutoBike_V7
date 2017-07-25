package autobike.stanley.idv.android_autobike_v7.navigationlayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class Navi_Sell_List_Detail_Page extends AppCompatActivity {

    private ImageView ivimage;
    private TextView tvSellNo,tvSellType,tvSellDate,tvSellStatus;
    private Bundle bundle ;
    private Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navi_sell_list_detail_page);
        findViews();
        bundle = this.getIntent().getExtras();
        new GetMotorModelImageBySellNo(ivimage).execute(Common.URL_MotorModelServlet,bundle.getString("sellno"),300);
        tvSellNo.setText(bundle.getString("sellno"));
        tvSellType.setText(bundle.getString("mototype"));
        tvSellDate.setText(bundle.getString("date"));
        tvSellStatus.setText(bundle.getString("status"));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvSellStatus.getText().toString().equals("訂單取消") || tvSellStatus.getText().toString().equals("正常結案")){

                    Toast.makeText(Navi_Sell_List_Detail_Page.this,"此訂單已取消",Toast.LENGTH_SHORT).show();
                }else{

                    final AlertDialog build=
                            new AlertDialog.Builder(Navi_Sell_List_Detail_Page.this)
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
                                            String url = Common.URL + "SecOrdServlet";
                                            try {
                                                new UpdateSellStatusTask().execute(url,bundle.getString("sellno"),"canceled").get();
                                                SellOrder sellordvo = (SellOrder) new GetSellVOBySellNoTask().execute(Common.URL_SecOrdServlet,bundle.getString("sellno")).get();
                                                tvSellStatus.setText(Common.ordStaturCheck(sellordvo.getStatus()));
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
        ivimage = (ImageView) findViewById(R.id.ivSellDetailImage);
        tvSellNo = (TextView) findViewById(R.id.tvSellDetailRentNo);
        tvSellType = (TextView) findViewById(R.id.tvSellDetailMotorType);
        tvSellDate = (TextView) findViewById(R.id.tvSellDetailStartDate);
        tvSellStatus = (TextView) findViewById(R.id.tvSellDetailStatus);
        btnCancel = (Button)findViewById(R.id.btn_sellord_cancel);
    }
}
