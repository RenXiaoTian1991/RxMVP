package xiaotian.ren.com.rxmvp.factory;


import xiaotian.ren.com.rxmvp.api.JokeApi;
import xiaotian.ren.com.rxmvp.api.RxService;

/**
 * Created by admin on 2016/5/5.
 */
public class MyFactory {

    protected static final Object monitor = new Object();
    public static JokeApi jokeApi = null;
    public static JokeApi getJokeService(){
        synchronized (monitor) {
            if (jokeApi == null) {
                jokeApi = new RxService().getJokeService();
            }
            return jokeApi;
        }
    }
}
