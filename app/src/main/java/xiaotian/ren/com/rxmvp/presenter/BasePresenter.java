package xiaotian.ren.com.rxmvp.presenter;


import android.app.Activity;
import android.content.Context;

import xiaotian.ren.com.rxmvp.ui.view.MvpView;

public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    private Context mContext;

    public BasePresenter(T mMvpView,Activity activity) {
        this.mMvpView = mMvpView;
        this.mContext = activity;
    }

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}

