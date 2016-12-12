package cn.xmrk.rkandroid.net.listener;


import cn.xmrk.rkandroid.net.entity.BaseResultEntity;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public abstract class HttpOnNextListener {

    /**
     * 成功后回调方法
     * @param
     */
    public abstract void onNext(BaseResultEntity result);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
    public  void onError(Throwable e){

    }

    /**
     * 取消回調
     */
    public void onCancel(){

    }
}
