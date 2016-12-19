package cn.xmrk.rkandroid.net.http;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.xmrk.rkandroid.db.DownloadDb;
import cn.xmrk.rkandroid.net.entity.DownState;
import cn.xmrk.rkandroid.net.entity.DownloadInfo;
import cn.xmrk.rkandroid.net.exception.HttpTimeException;
import cn.xmrk.rkandroid.net.exception.RetryWhenNetworkException;
import cn.xmrk.rkandroid.net.listener.DownLoadListener.DownloadInterceptor;
import cn.xmrk.rkandroid.net.subscribers.ProgressDownSubscriber;
import cn.xmrk.rkandroid.net.utils.AppUtil;
import cn.xmrk.rkandroid.utils.StringUtil;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * http下载处理类
 * Created by WZG on 2016/7/16.
 */
public class HttpDownManager {

    /*超时设置*/
    private static final int DEFAULT_TIMEOUT = 6;

    /*记录下载数据*/
    private Set<DownloadInfo> downInfos;
    /*回调sub队列*/
    private HashMap<String, ProgressDownSubscriber> subMap;
    /*单利对象*/
    private volatile static HttpDownManager INSTANCE;


    private HttpDownManager() {
        downInfos = new HashSet<>();
        subMap = new HashMap<>();
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 开始下载
     */
    public void startDown(final DownloadInfo info) {
        //这边用下载名称和url来表示唯一
        /*正在下载不处理*/
        String name = StringUtil.getFileName(info.getFilePath());
        if (info == null || subMap.get(name + "_" + info.getUrl()) != null) {
            subMap.get(info.getUrl()).setDownInfo(info);
            return;
        }
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(info);
        /*记录回调sub*/
        subMap.put(info.getUrl(), subscriber);
        /*获取service，多次请求公用一个sercie*/
        HttpService httpService;
        if (downInfos.contains(info)) {
            httpService = info.getService();
        } else {
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //手动创建一个OkHttpClient并设置超时时间
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(AppUtil.getBasUrl(info.getUrl()))
                    .build();
            httpService = retrofit.create(HttpService.class);
            info.setService(httpService);
            downInfos.add(info);
        }
        /*得到rx对象-上一次下载的位置开始下载*/
        httpService.downloadFile("bytes=" + info.getReadLength() + "-", info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(new Func1<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo call(ResponseBody responseBody) {
                        try {
                            AppUtil.writeCache(responseBody, new File(info.getFilePath()), info);
                        } catch (IOException e) {
                            /*失败抛出异常*/
                            throw new HttpTimeException(e.getMessage());
                        }
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);

    }


    /**
     * 停止下载
     */
    public void stopDown(DownloadInfo info) {
        if (info == null) return;
        info.setState(DownState.STOP.getState());
        info.getListener().onStop();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe();
            subMap.remove(info.getUrl());
        }
        /*保存数据库信息和本地文件*/
        DownloadDb.getInstance().saveUpdateDownloadInfo(info);
    }


    /**
     * 暂停下载
     *
     * @param info
     */
    public void pause(DownloadInfo info) {
        if (info == null) return;
        info.setState(DownState.PAUSE.getState());
        info.getListener().onPuase();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe();
            subMap.remove(info.getUrl());
        }
        /*这里需要讲info信息写入到数据中，可自由扩展，用自己项目的数据库*/
        DownloadDb.getInstance().saveUpdateDownloadInfo(info);
    }

    /**
     * 停止全部下载
     */
    public void stopAllDown() {
        for (DownloadInfo downInfo : downInfos) {
            stopDown(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }

    /**
     * 暂停全部下载
     */
    public void pauseAll() {
        for (DownloadInfo downInfo : downInfos) {
            pause(downInfo);
        }
        subMap.clear();
        downInfos.clear();
    }


    /**
     * 返回全部正在下载的数据
     *
     * @return
     */
    public Set<DownloadInfo> getDownInfos() {
        return downInfos;
    }

    /**
     * 移除下载数据
     *
     * @param info
     */
    public void remove(DownloadInfo info) {
        subMap.remove(info.getUrl());
        downInfos.remove(info);
    }

}
