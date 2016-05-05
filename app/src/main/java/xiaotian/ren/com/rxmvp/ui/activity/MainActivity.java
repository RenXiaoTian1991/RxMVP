package xiaotian.ren.com.rxmvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import xiaotian.ren.com.rxmvp.R;
import xiaotian.ren.com.rxmvp.common.AutoLoadRecylerView;
import xiaotian.ren.com.rxmvp.common.AutoLoadRecylerView.loadMoreListener;
import xiaotian.ren.com.rxmvp.common.DividerItemDecoration;
import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.presenter.JokePresenter;
import xiaotian.ren.com.rxmvp.ui.adapter.JokeAdapter;
import xiaotian.ren.com.rxmvp.ui.view.JokeView;

public class MainActivity extends BaseActivity<JokePresenter> implements JokeView,
        SwipeRefreshLayout.OnRefreshListener, loadMoreListener {

    @Bind(R.id.record_recycleview)
    AutoLoadRecylerView recordRecycleview;
    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;
    @Bind(R.id.common_error_txt)
    TextView commonErrorTxt;
    @Bind(R.id.retry_btn)
    Button retryBtn;
    @Bind(R.id.common_error)
    RelativeLayout commonError;
    @Bind(R.id.joke_refresh_layout)
    SwipeRefreshLayout jokeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private int page = 1;
    private List<BaseData> jokeList;
    private JokeAdapter jokeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        loadData();
                    }
                });

    }

    @Override
    protected void initPresenter() {
        mPresenter = new JokePresenter(this,this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mProgressBar.setVisibility(View.GONE);
    }

    protected void initView() {
        jokeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recordRecycleview.setLayoutManager(layoutManager);
        recordRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST));
        recordRecycleview.setLoadMoreListener(this);
    }

    protected void initData() {
        jokeList = new ArrayList<>();
        jokeAdapter = new JokeAdapter(jokeList);
        recordRecycleview.setAdapter(jokeAdapter);

    }

    private void loadData() {
        mPresenter.loadList(page);
        page++;
    }

    @Override
    public void onRefresh() {
        page = 1;
        loadData();
    }


    @Override
    public void onLoadMore() {
        loadData();
    }

    /**
     * @param data 回调 以下三个方法是presenter回调的函数 用于更新界面
     */
    @Override
    public void refresh(List<BaseData> data) {
        commonError.setVisibility(View.GONE);
        jokeList.clear();
        jokeList.addAll(data);
        jokeAdapter.notifyDataSetChanged();
        jokeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadMore(List<BaseData> data) {
        commonError.setVisibility(View.GONE);
        jokeList.addAll(data);
        jokeAdapter.notifyDataSetChanged();
        recordRecycleview.setLoading(false);
    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {
        super.showError(msg, onClickListener);
        commonError.setVisibility(View.VISIBLE);
        recordRecycleview.setLoading(false);
        jokeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.retry_btn)
    void setRetryBtnonClick() {
        onRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
