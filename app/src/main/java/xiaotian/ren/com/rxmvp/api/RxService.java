package xiaotian.ren.com.rxmvp.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SkyEyesStion on 2016/2/26.
 */
public class RxService {
    private static final String BASETESTURL = "http://apis.baidu.com/showapi_open_bus/";
    public JokeApi jokeService;

    public RxService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor
                (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASETESTURL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jokeService = retrofit.create(JokeApi.class);
    }

    public JokeApi getJokeService(){
        return jokeService;
    }
}
