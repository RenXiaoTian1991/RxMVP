package xiaotian.ren.com.rxmvp.init;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by admin on 2016/5/6.
 */
public class InitApp {
    public InitApp() {
    }

    public void init(){
        //from张存放了一系列的runnable对象，四秒以后执行订阅，foreach表示按顺序执行
        //注意from的特性，他会将*（）中的代码立即执行，如果需要延迟执行，则需要defer操作符进行处理
        Observable.from(new Runnable[]{getOneRun(),getTwoRun()})
                .delay(4, TimeUnit.SECONDS)
                .delaySubscription(2, TimeUnit.SECONDS)
                .forEach(new Action1<Runnable>() {
                    @Override
                    public void call(Runnable runnable) {
                        if (runnable != null) {
                            runnable.run();
                        }
                    }
                });
    }

    public Runnable getOneRun(){
        return new Runnable() {
            @Override
            public void run() {
                Log.e("abc","我是第一个任务");
            }
        };
    }

    public Runnable getTwoRun(){
        return new Runnable() {
            @Override
            public void run() {
                Log.e("abc","我是第二个任务");
            }
        };
    }

}
