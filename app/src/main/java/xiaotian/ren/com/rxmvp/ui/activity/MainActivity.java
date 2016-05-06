package xiaotian.ren.com.rxmvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import me.yokeyword.imagepicker.ImagePicker;
import me.yokeyword.imagepicker.callback.CallbackForImagePicker;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import xiaotian.ren.com.rxmvp.R;
import xiaotian.ren.com.rxmvp.common.AutoLoadRecylerView;
import xiaotian.ren.com.rxmvp.common.AutoLoadRecylerView.loadMoreListener;
import xiaotian.ren.com.rxmvp.common.DividerItemDecoration;
import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.presenter.JokePresenter;
import xiaotian.ren.com.rxmvp.ui.adapter.JokeAdapter;
import xiaotian.ren.com.rxmvp.ui.view.JokeView;
import xiaotian.ren.com.rxmvp.util.PreferenceUtils;
import xiaotian.ren.com.rxmvp.util.ToolBarHandler;

/**
 * Created by JDD on 2016/4/8.
 */
public class MainActivity extends BaseActivity<JokePresenter> implements JokeView,
        SwipeRefreshLayout.OnRefreshListener, loadMoreListener {

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
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
    @Bind(R.id.main_fab)
    FloatingActionButton fab;
    private LinearLayoutManager layoutManager;
    private int page = 1;
    private List<BaseData> jokeList;
    private JokeAdapter jokeAdapter;
    private ImagePicker imagePicker;
    private boolean hasPermission;
    private ToolBarHandler mToolBarHandler;
    public static final String[] WRITE_EXTERNAL_STORAGE = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        loadData();
                        //请求权限
                        requestPerm();
                        mToolBarHandler.showToolBar(true);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });

    }

    /*
    根据版本是否大于6.0，大于再次进行权限判断，没有则进行权限请求
     */
    private void requestPerm() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                this.requestPermissions(WRITE_EXTERNAL_STORAGE, 2);
            }else {
                hasPermission = true;
                PreferenceUtils.setPrefBoolean("isPer",false);
            }
    }


    protected void initView() {
        mToolBarHandler = new ToolBarHandler(this);
        imagePicker = new ImagePicker(this);
        jokeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recordRecycleview.setLayoutManager(layoutManager);
        recordRecycleview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST));
        recordRecycleview.setLoadMoreListener(this);
        //这句一定要在setSupport之前调用，否则不起作用
        mToolBar.setTitle("xiaotian");
        setSupportActionBar(mToolBar);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 委托处理
        imagePicker.delegateActivityResult(requestCode, resultCode, data);
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

    /*
    有权限则进行调用图库
     */
    @OnClick(R.id.main_fab)
    void setFabClick(){
        if(!PreferenceUtils.getPrefBoolean("isPer",false)){
            return;
        }
        imagePicker.openImagePiker(true, new CallbackForImagePicker() {
            @Override
            public void onError(Exception error) {
            }

            @Override
            public void onComplete(List<String> imagePath) {
                Log.e("abc", imagePath.size() + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    /*
    请求权限的回调，用户授予权限则进行标志位处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           hasPermission = true;
           PreferenceUtils.setPrefBoolean("isPer",true);
        }else {
            hasPermission = false;
            PreferenceUtils.setPrefBoolean("isPer",false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mToolBarHandler.MenuClickListener(item);
        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolBar(){
        return mToolBar;
    }

    public FloatingActionButton getFloatBtn(){
        return fab;
    }

}
