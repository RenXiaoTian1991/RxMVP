package xiaotian.ren.com.rxmvp.util;

import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.interfa.SubscribeInterface;
import xiaotian.ren.com.rxmvp.ui.view.MvpView;

/**
 * Created by admin on 2016/5/5.
 */
public class PresenterUtil {
    public static Subscriber<Object> getSubscribe(final MvpView view, final SubscribeInterface subscribeInterface){
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.getMessage());
                view.showError(null, null);
            }

            @Override
            public void onNext(Object o) {
                List<BaseData> datas = (List<BaseData>) o;
                subscribeInterface.onNext(datas);
            }
        };
    }
}
