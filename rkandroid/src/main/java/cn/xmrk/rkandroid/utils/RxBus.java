package cn.xmrk.rkandroid.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Au61 on 2016/9/12.
 */

/**
 * RxBus
 * Created by YoKeyword on 2015/6/17.
 */
public class RxBus {

    /** 用法
     Subscription rxSubscription = RxBus.getDefault().toObservable(WeatherPost.class)
     .subscribe(new Action1<WeatherPost>() {
    @Override
    public void call(WeatherPost post) {
    if (StringUtil.isEqualsString(post.fragmnetTag, AqiInfoFragment.this.fragmentTag)) {
    Log.i("info-->", "接收到了" + fragmentTag + "_" + post.weatherInfo.getCity());
    AqiInfoFragment.this.info = post.weatherInfo;
    initData();
    }
    }
    }
     , new Action1<Throwable>() {
    @Override
    public void call(Throwable throwable) {
    CommonUtil.showToast(throwable.getMessage());
    }
    }
     );
     **/

    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;

    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }

    // 发送一个新的事件
    public void post (Object o) {
        bus.onNext(o);
    }


    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}