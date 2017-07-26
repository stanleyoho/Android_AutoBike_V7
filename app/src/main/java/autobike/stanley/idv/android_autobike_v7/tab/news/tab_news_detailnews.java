package autobike.stanley.idv.android_autobike_v7.tab.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessageGetImageTask;

public class tab_news_detailnews extends AppCompatActivity {

    private ImageView ivNewsDetailImage;
    private TextView tvNewsDetailContent;
    private Profile profile;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_news_detailnews);
        profile = new Profile(this);
        bundle = getIntent().getExtras();
        News news = (News)bundle.getSerializable("news");
        findViews();
        tvNewsDetailContent.setText(news.getCont());
        String url = Common.URL + "MesBoardServlet";
        String newsno = news.getNewsno();
        new GetNewsImageTask(ivNewsDetailImage).execute(Common.URL_NewsServlet,newsno,250);
    }

    private void findViews() {
        tvNewsDetailContent = (TextView) findViewById(R.id.tvNewsDetailContent);
        ivNewsDetailImage = (ImageView) findViewById(R.id.ivNewsDetailImage);
    }
}
