package cn.xmrk.rkandroid.net.entity;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpOnNextListener;
import cn.xmrk.rkandroid.net.subscribers.ProgressSubscriber;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 请求数据统一封装类
 * Created by WZG on 2016/7/16.
 */
public abstract class BaseEntity implements Func1<BaseResultEntity, BaseResultEntity> {


    //rx生命周期管理
    private RxAppCompatActivity rxAppCompatActivity;

    /*sub预处理类*/
    protected ProgressSubscriber progressSubscriber;


    public BaseEntity(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        this(listener, rxAppCompatActivity, null, true);
    }


    public BaseEntity(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, boolean isShow) {
        this(listener, rxAppCompatActivity, null, isShow);
    }

    public BaseEntity(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String dialogMsg) {
        this(listener, rxAppCompatActivity, dialogMsg, true);
    }


    public BaseEntity(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String dialogMsg, boolean isShow) {
        progressSubscriber = new ProgressSubscriber(listener, rxAppCompatActivity, dialogMsg, isShow);
        this.rxAppCompatActivity = rxAppCompatActivity;
    }

    /**
     * 获取当前rx生命周期
     *
     * @return
     */
    public RxAppCompatActivity getRxAppCompatActivity() {
        return rxAppCompatActivity;
    }

    /**
     * 设置参数
     *
     * @param methods
     * @return
     */
    public abstract Observable getObservable(HttpService methods);

    /**
     * 设置回调sub
     *
     * @return
     */
    public Subscriber getSubscirber() {
        return progressSubscriber;

    }

}
