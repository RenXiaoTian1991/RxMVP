package xiaotian.ren.com.rxmvp.ui.view;

import java.util.List;

import xiaotian.ren.com.rxmvp.interfa.BaseData;
import xiaotian.ren.com.rxmvp.model.ContentlistEntity;

/**
 * Created by JDD on 2016/4/21 0021.
 */
public interface JokeView extends MvpView {
    void refresh(List<BaseData> data);

    void loadMore(List<BaseData>  data);

}
