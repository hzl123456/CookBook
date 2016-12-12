package cn.xmrk.rkandroid.net.entity;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpOnNextListener;
import rx.Observable;

/**
 * 测试数据
 * Created by WZG on 2016/7/16.
 */
public abstract class SubjectPostApi extends BaseEntity {
    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        this(listener, rxAppCompatActivity, null, true);
    }


    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, boolean isShow) {
        this(listener, rxAppCompatActivity, null, isShow);
    }

    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String dialogMsg) {
        this(listener, rxAppCompatActivity, dialogMsg, true);
    }


    public SubjectPostApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity, String dialogMsg, boolean isShow) {
        super(listener, rxAppCompatActivity, dialogMsg, isShow);
    }

    @Override
    public BaseResultEntity call(BaseResultEntity resultEntity) {
        return resultEntity;
    }

    /**
     * 调用的方法
     **/
    @Override
    public abstract Observable getObservable(HttpService methods);
}
