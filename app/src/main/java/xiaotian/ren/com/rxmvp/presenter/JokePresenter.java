package xiaotian.ren.com.rxmvp.presenter;

import android.app.Activity;

import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import xiaotian.ren.com.rxmvp.factory.MyFactory;
import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.interfa.SubscribeInterface;
import xiaotian.ren.com.rxmvp.model.ContentlistEntity;
import xiaotian.ren.com.rxmvp.model.JokeEntity;
import xiaotian.ren.com.rxmvp.ui.view.JokeView;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import xiaotian.ren.com.rxmvp.util.PresenterUtil;

public class JokePresenter extends BasePresenter<JokeView> {

    public JokePresenter(JokeView mMvpView, Activity activity) {
        super(mMvpView, activity);
    }


    public void loadList(final int page) {
        MyFactory.getJokeService()
                .getJoke(page)
                .subscribeOn(Schedulers.io())
                .map(new Func1<JokeEntity, List<ContentlistEntity>>() {
                    @Override
                    public List<ContentlistEntity> call(JokeEntity jokeEntity) {
                        return jokeEntity.getShowapi_res_body().getContentlist();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(PresenterUtil.getSubscribe(getMvpView(), new SubscribeInterface() {
                    @Override
                    public void onNext(List<BaseData> datas) {
                        List<BaseData> data = datas;
                        if (page == 1) {
                            getMvpView().refresh(datas);
                        } else {
                            getMvpView().loadMore(datas);
                        }
                    }
                }));
//                .subscribe(new Observer<List<ContentlistEntity>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.e(e.getMessage());
//                        getMvpView().showError(null, null);
//                    }
//
//                    @Override
//                    public void onNext(List<ContentlistEntity> contentlistEntities) {
//                        if (page == 1) {
//                            getMvpView().refresh(contentlistEntities);
//                        } else {
//                            getMvpView().loadMore(contentlistEntities);
//                        }
//                    }
//                });

    }
}
