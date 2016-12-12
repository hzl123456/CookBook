package cn.xmrk.rkandroid.net.entity;


import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpProgressOnNextListener;
import rx.Observable;

/**
 * apk下载请求类
 * Created by WZG on 2016/10/20.
 */

public class DownApkApi extends BaseDownEntity{

    public DownApkApi(String url, HttpProgressOnNextListener listener) {
        super(url,listener);
    }

    @Override
    public Observable getObservable(HttpService methods) {
        return methods.downloadFile(getUrl());
    }
}
