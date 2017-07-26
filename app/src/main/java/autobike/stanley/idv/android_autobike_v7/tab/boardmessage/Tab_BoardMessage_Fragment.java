package autobike.stanley.idv.android_autobike_v7.tab.boardmessage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;

public class Tab_BoardMessage_Fragment extends Fragment {
    private static final String TAG = "BoardMesListFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvBoardMessage;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_boardmessage_fragment, container, false);
        bundle  = new Bundle();
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.boardMesswipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllSpots();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rvBoardMessage = (RecyclerView) view.findViewById(R.id.rvBoardMes);
        rvBoardMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton btAdd = (FloatingActionButton) view.findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Tab_BoardMessage_AddMessage.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void showAllSpots() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "MesBoardServlet";
            List<BoardMessage> boardmes = null;
            try {
                boardmes = new BoardMesGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (boardmes == null || boardmes.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoSpotsFound);
            } else {
                rvBoardMessage.setAdapter(new BoardMessageRecyclerViewAdapter(getActivity(), boardmes));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllSpots();
    }

    private class BoardMessageRecyclerViewAdapter extends RecyclerView.Adapter<BoardMessageRecyclerViewAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private List<BoardMessage> boardmesList;

        public BoardMessageRecyclerViewAdapter(Context context, List<BoardMessage> boardmesList) {
            layoutInflater = LayoutInflater.from(context);
            this.boardmesList = boardmesList;
        }


        @Override
        public BoardMessageRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.tab_boardmessage_recycleitem, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final BoardMessageRecyclerViewAdapter.MyViewHolder myViewHolder, int position) {

            final BoardMessage boardmes = boardmesList.get(position);
            String url = Common.URL + "MesBoardServlet";
            String mesno = boardmes.getMesno();
            int imageSize = 250;
            new BoardMessageGetImageTask(myViewHolder.imageView).execute(url, mesno, imageSize);
            //test
            myViewHolder.tvName.setText(boardmes.getCont());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            myViewHolder.tvPhoneNo.setText("建立時間 : "+sdf.format(boardmes.getDate()));

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("boardmes",boardmes);
                    Intent intent = new Intent(getActivity(),Tab_BoardMessage_DetailMessage.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvName, tvPhoneNo;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.ivBoardMesImage);
                tvName = (TextView) itemView.findViewById(R.id.tvBoardMesTitle);
                tvPhoneNo = (TextView) itemView.findViewById(R.id.tvBoardMesDetail);

            }
        }

        @Override
        public int getItemCount() {
            return boardmesList.size();
        }
    }
}

