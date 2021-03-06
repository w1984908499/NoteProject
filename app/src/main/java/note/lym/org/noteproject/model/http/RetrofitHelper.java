package note.lym.org.noteproject.model.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import note.lym.org.noteproject.BuildConfig;
import note.lym.org.noteproject.app.Constants;
import note.lym.org.noteproject.model.bean.Belle;
import note.lym.org.noteproject.model.bean.Health;
import note.lym.org.noteproject.model.bean.HealthDetail;
import note.lym.org.noteproject.model.bean.HealthList;
import note.lym.org.noteproject.model.bean.Joke;
import note.lym.org.noteproject.model.bean.LookerGirl;
import note.lym.org.noteproject.model.bean.MaySisterData;
import note.lym.org.noteproject.model.bean.MoreType;
import note.lym.org.noteproject.model.bean.NewsDetailBean;
import note.lym.org.noteproject.model.bean.NewsList;
import note.lym.org.noteproject.model.bean.SisterClassList;
import note.lym.org.noteproject.model.bean.SisterList;
import note.lym.org.noteproject.model.bean.TextJoke;
import note.lym.org.noteproject.model.bean.xxxData;
import note.lym.org.noteproject.utils.SystemUtil;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {

    private static OkHttpClient sOkHttpClient = null;
    private static NoteApis sNoteApis = null;

    public RetrofitHelper() {
        init();
    }

    private void init() {
        initOkHttp();
        sNoteApis = getOnlyApiService();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // https://drakeet.me/retrofit-2-0-okhttp-3-0-config
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        // http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!SystemUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (SystemUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 正常访问同一请求接口（多次访问同一接口），给10秒缓存，超过时间重新发送请求，否则取缓存数据
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static NoteApis getOnlyApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sNoteApis.HOST)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(NoteApis.class);
    }


    /**
     * 初始化通用的观察者
     *
     * @param observable 观察者
     */
    public ResourceSubscriber startObservable(Flowable observable, ResourceSubscriber subscriber) {
        return (ResourceSubscriber) observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }

    public Flowable<HttpResponse<List<xxxData>>> fetchGetData(int size, int page) {
        return sNoteApis.getData(size, page);
    }

    public Flowable<Belle> getBelleData(int page,int count) {
        return sNoteApis.getBelleData(page,count);
    }

    public Flowable<NewsList> getNewsList(int page) {
        return sNoteApis.getNews(page);
    }

    public Flowable<Map<String, NewsDetailBean>> getNewsDetail(String id) {
        return sNoteApis.getNewsDetail(id);
    }

    public Flowable<Joke> getJokeList(Map<String, String> map) {
        return sNoteApis.getJokes(map);
    }

    public Flowable<TextJoke> getTextJokeList(Map<String,String> map){
        return sNoteApis.getTextJoke(map);
    }

    public Flowable<Health> getHealthClassifyList(Map<String,String> map){
        return sNoteApis.getHealthList(map);
    }

    public Flowable<HealthList> getHealthList(Map<String,String> map){
        return sNoteApis.getHealthListData(map);
    }

    public Flowable<HealthDetail> getHealthDetail(Map<String,String> map){
        return sNoteApis.getHealthDetailData(map);
    }

    public Flowable<SisterList> getSisterClassifyList(Map<String,String> map){
        return sNoteApis.getSisterListData(map);
    }

    public Flowable<SisterClassList> getSisterClassifyDataList(Map<String,String> map){
        return sNoteApis.getSisterClassifyList(map);
    }

    public Flowable<MaySisterData> getMaySisterData(Map<String,String> map){
        return sNoteApis.getMaySisterData(map);
    }

    public Flowable<MoreType> getMoreTypeData(Map<String,String> map){
        return sNoteApis.getPersonToLife(map);
    }

    public Flowable<LookerGirl> getLookerGirlData(Map<String,String> map){
        return sNoteApis.getLookerGirl(map);
    }


}
