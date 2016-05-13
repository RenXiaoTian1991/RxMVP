package xiaotian.ren.com.rxmvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import xiaotian.ren.com.rxmvp.presenter.BasePresenter;
import xiaotian.ren.com.rxmvp.ui.view.MvpView;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements MvpView {

    protected P mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initPresenter();
//        checkPresenterIsNull();
        mPresenter.attachView(this);
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract void initPresenter();

    protected abstract int getLayoutId();

    private void checkPresenterIsNull() {
        if(mPresenter == null){
            throw new IllegalStateException("主控制器不能为空，请检查代码");
        }
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmpty(String msg, View.OnClickListener onClickListener, int imageId) {

    }

    @Override
    public void showNetError(View.OnClickListener onClickListener) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
