package xiaotian.ren.com.rxmvp;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by JDD on 2016/4/8.
 */
public class BaseApp extends Application {

    private static BaseApp baseApp;
    public static BaseApp getApplication(){
        return baseApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        baseApp = this;
    }
}
