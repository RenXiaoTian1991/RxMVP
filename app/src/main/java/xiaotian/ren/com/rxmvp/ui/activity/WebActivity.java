package xiaotian.ren.com.rxmvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import xiaotian.ren.com.rxmvp.R;

/**
 * Created by lenovo on 2016/5/13.
 */
public class WebActivity extends BaseActivity {
    @Bind(R.id.web_view)
    WebView mWebView;
    private static final String url = "http://www.jianshu.com/p/d614bb028588";
    @Override
    protected void initData() {
        //初始化webview
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF -8");

        //载入js
        mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");

        mWebView.setWebViewClient(new WebViewClient() {
            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);

                super.onPageStarted(view, url, favicon);
            }

            // 网页跳转
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);

            }

            // 网页加载结束
            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);

                super.onPageFinished(view, url);

                // html加载完成之后，添加监听图片的点击js函数
                addImageClickListner();
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //出现页面错误的时候，不让webView显示了。同时跳出一个错误Toast
                mWebView.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            // 网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });

        mWebView.loadUrl(url);

    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    // js通信接口
    public class JavascriptInterface {

        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            System.out.println(img);
            Intent intent = new Intent();
            intent.putExtra(ShowWebImageActivity.IMG, img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
