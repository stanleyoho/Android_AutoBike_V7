package autobike.stanley.idv.android_autobike_v7.tab.boardmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.Profile;
import autobike.stanley.idv.android_autobike_v7.R;

public class Tab_BoardMessage_DetailMessage extends AppCompatActivity {

    private ImageView ivMessageImage;
    private TextView tvMessageContent;
    private Profile profile;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_board_message_detailmessage);
        profile = new Profile(this);
        bundle = getIntent().getExtras();
        BoardMessage boardMessage = (BoardMessage)bundle.getSerializable("boardmes");
        findViews();
        tvMessageContent.setText(boardMessage.getCont());
        String url = Common.URL + "MesBoardServlet";
        String mesno = boardMessage.getMesno();
        int imageSize = 250;
        new BoardMessageGetImageTask(ivMessageImage).execute(url, mesno, imageSize);
    }

    private void findViews() {
        ivMessageImage = (ImageView) findViewById(R.id.ivMessageImage);
        tvMessageContent = (TextView) findViewById(R.id.tvMessageContent);
    }

}
