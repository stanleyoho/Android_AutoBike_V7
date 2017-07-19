package autobike.stanley.idv.android_autobike_v7.tab.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMesGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessage;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_Fragment;

public class Tab_News_Fragment extends Fragment {

    private final static String TAG = "NewsFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvNews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_news_fragment, container, false);
        rvNews = (RecyclerView) view.findViewById(R.id.rvNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout =
                (SwipeRefreshLayout) view.findViewById(R.id.newsswipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                showAllNews();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        showAllNews();
    }

    private void showAllNews() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "NewsServlet";
            List<News> newsList = null;

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            try {
                newsList = new NewsGetAllTask().execute(url).get();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (newsList == null || newsList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoMotorFound);
            } else {
                rvNews.setAdapter(new Tab_News_Fragment.NewsRecyclerViewAdapter(getActivity(), newsList));
            }
            progressDialog.cancel();

        } else {
            Common.showToast(getActivity(), R.string.msg_NoMotorFound);
        }
    }

    private class NewsRecyclerViewAdapter extends RecyclerView.Adapter<Tab_News_Fragment.NewsRecyclerViewAdapter.ViewHolder> {
        private LayoutInflater layoutInflater;
        private List<News> newsList;
        private boolean[] newsExpanded;

        public NewsRecyclerViewAdapter(Context context, List<News> newsList) {
            layoutInflater = LayoutInflater.from(context);
            this.newsList = newsList;
            newsExpanded = new boolean[newsList.size()];
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ivimage;
            TextView NewsTitle, NewsDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                ivimage = (ImageView)itemView.findViewById(R.id.ivNewsImage);
                NewsTitle = (TextView) itemView.findViewById(R.id.tvNewsTitle);
                NewsDetail = (TextView) itemView.findViewById(R.id.tvNewsDetail);
            }
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        @Override
        public Tab_News_Fragment.NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.tab_news_recycleitem, parent, false);
            return new Tab_News_Fragment.NewsRecyclerViewAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final Tab_News_Fragment.NewsRecyclerViewAdapter.ViewHolder viewHolder, int position) {
            News news = newsList.get(position);
            new GetNewsImageTask(viewHolder.ivimage).execute(Common.URL_NewsServlet,news.getNewsno(),250);
            viewHolder.NewsTitle.setText("最新消息編號 : " + news.getNewsno());
            viewHolder.NewsDetail.setText("消息類別: " + news.getTitle() + "\r\n" + "消息內容: " + news.getCont() + "\r\n" );
//            viewHolder.motorNewsDetail.setVisibility(
//                    motorExpanded[position] ? View.VISIBLE : View.GONE);
//            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    expand(viewHolder.getAdapterPosition());
//                }
//            });
        }

        private void expand(int position) {
            // 被點擊的資料列才會彈出內容，其他資料列的內容會自動縮起來
            // for (int i=0; i<newsExpanded.length; i++) {
            // newsExpanded[i] = false;
            // }
            // newsExpanded[position] = true;/////
//
//            motorExpanded[position] = !motorExpanded[position];
//            notifyDataSetChanged();
        }
    }
}
