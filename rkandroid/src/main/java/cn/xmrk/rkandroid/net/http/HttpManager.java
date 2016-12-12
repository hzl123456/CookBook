package cn.xmrk.rkandroid.net.http;


import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.xmrk.rkandroid.config.RKConfigHelper;
import cn.xmrk.rkandroid.net.entity.BaseEntity;
import cn.xmrk.rkandroid.net.entity.UploadFile;
import cn.xmrk.rkandroid.net.exception.RetryWhenNetworkException;
import cn.xmrk.rkandroid.net.listener.upload.ProgressRequestBody;
import cn.xmrk.rkandroid.net.listener.upload.UploadProgressListener;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * http交互处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpManager {

    private static final Logger log = Logger.getLogger(HttpManager.class);

    /*超时设置*/
    private static final int DEFAULT_TIMEOUT = 6;

    private HttpService httpService;

    private volatile static HttpManager INSTANCE;

    //构造方法私有
    private HttpManager() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //TODO 这边是需要添加通用的header，不需要的情况下可以把这个去掉
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("apikey", "7c2dfb674b5a73d2ccf204c69a4ed44a")
                        .build();
                if (RKConfigHelper.getInstance().getRKConfig().isDebug()) {
                    log.debug("request-->" + request.url().toString());
                }
                return chain.proceed(request);
            }
        });
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(RKConfigHelper.getInstance().getRKConfig().getBaseUrl())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    //获取单例
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 处理http请求
     *
     * @param basePar 封装的请求数据
     */
    public void doHttpDeal(BaseEntity basePar) {
        Observable observable = basePar.getObservable(httpService)
                /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*生命周期管理*/
                .compose(basePar.getRxAppCompatActivity().bindToLifecycle())
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*结果判断*/
                .map(basePar);
        /*数据回调*/
        observable.subscribe(basePar.getSubscirber());
    }

    /**
     * 文件的上传
     **/
    public MultipartBody filesToMultipartBody(Map<String, String> params, List<UploadFile> files, UploadProgressListener listener) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        UploadFile uploadFile = null;
        for (int i = 0; i < files.size(); i++) {
            uploadFile = files.get(i);
            ProgressRequestBody requestBody = new ProgressRequestBody(RequestBody.create(MediaType.parse("application/otcet-stream"), uploadFile.file), uploadFile.file.getName(), listener);
            builder.addFormDataPart(uploadFile.name, uploadFile.file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        if (params != null) {
            // map 里面是请求中所需要的 key 和 value
            for (Map.Entry entry : params.entrySet()) {
                builder.addFormDataPart(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        }
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
}
