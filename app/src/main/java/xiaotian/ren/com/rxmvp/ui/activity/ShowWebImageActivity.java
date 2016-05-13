package xiaotian.ren.com.rxmvp.ui.activity;

import com.bm.library.PhotoView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import xiaotian.ren.com.rxmvp.R;

/**
 * Created by lenovo on 2016/5/13.
 */
public class ShowWebImageActivity extends BaseActivity{

    public static final String IMG = "img";
    private String url;
    @Bind(R.id.show_webimage_imageview)
    PhotoView image;
    @Override
    protected void initData() {

        url = getIntent().getStringExtra(IMG);
        Picasso.with(this).load(url).into(image);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image;
    }
}
