package autobike.stanley.idv.android_autobike_v7.tab.news;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.List;

import autobike.stanley.idv.android_autobike_v7.Common;
import autobike.stanley.idv.android_autobike_v7.R;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMesGetAllTask;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.BoardMessage;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_DetailMessage;
import autobike.stanley.idv.android_autobike_v7.tab.boardmessage.Tab_BoardMessage_Fragment;

public class Tab_News_Fragment extends Fragment {

    private final static String TAG = "NewsFragment";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rvNews;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_news_fragment, container, false);
        bundle  = new Bundle();
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
            TextView tvNewsDate, NewsDetail;

            public ViewHolder(View itemView) {
                super(itemView);
                ivimage = (ImageView)itemView.findViewById(R.id.ivNewsImage);
                tvNewsDate = (TextView) itemView.findViewById(R.id.tvNewsDate);
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
            final News news = newsList.get(position);
            new GetNewsImageTask(viewHolder.ivimage).execute(Common.URL_NewsServlet,news.getNewsno(),250);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            viewHolder.tvNewsDate.setText("建立時間 : " + sdf.format(news.getDate()));
            viewHolder.NewsDetail.setText(news.getCont() );
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundle.putSerializable("news",news);
                    Intent intent = new Intent(getActivity(),tab_news_detailnews.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}
