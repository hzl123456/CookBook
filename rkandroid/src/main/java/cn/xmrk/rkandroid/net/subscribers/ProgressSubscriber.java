package cn.xmrk.rkandroid.net.subscribers;

import android.content.Context;
import android.widget.Toast;

import org.apache.log4j.Logger;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import cn.xmrk.rkandroid.config.RKConfigHelper;
import cn.xmrk.rkandroid.net.entity.BaseResultEntity;
import cn.xmrk.rkandroid.net.listener.HttpOnNextListener;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.DialogUtil;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by WZG on 2016/7/16.
 */
public class ProgressSubscriber extends Subscriber<BaseResultEntity> {

    private static final Logger log = Logger.getLogger(ProgressSubscriber.class);
    /*是否弹框*/
    private boolean showPorgress = true;
    //回调接口
    private HttpOnNextListener mSubscriberOnNextListener;
    //弱引用反正内存泄露
    private WeakReference<Context> mActivity;
    //弹窗
    private DialogUtil dialogUtil;
    //弹窗的文字提示
    private String msg;


    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context context) {
        this(mSubscriberOnNextListener, context, null, true);
    }

    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context context, boolean showPorgress) {
        this(mSubscriberOnNextListener, context, null, showPorgress);
    }

    /**
     * 初始化
     *
     * @param mSubscriberOnNextListener
     * @param context
     * @param showPorgress              是否需要加载框
     * @param msg                       弹窗的文字显示
     */
    public ProgressSubscriber(HttpOnNextListener mSubscriberOnNextListener, Context context, String msg, boolean showPorgress) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mActivity = new WeakReference<>(context);
        this.msg = msg;
        this.showPorgress = showPorgress;
        if (this.showPorgress) {
            initProgressDialog();
        }
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog() {
        if (dialogUtil == null) {
            dialogUtil = DialogUtil.newInstance(mActivity.get());
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (showPorgress) {
            dialogUtil.showProgress(msg);
        }
    }


    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        if (showPorgress) {
            dialogUtil.dismiss();
        }
    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Context context = mActivity.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
    }

    @Override
    public void onNext(BaseResultEntity resultEntity) {
        if (RKConfigHelper.getInstance().getRKConfig().isDebug()) {
            log.debug("result-->" + CommonUtil.getGson().toJson(resultEntity));
        }
        //请求成功，并且flag为true
        if (resultEntity.status) {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onNext(resultEntity);
            }
        } else {
            if (mSubscriberOnNextListener != null) {
                mSubscriberOnNextListener.onError(new Throwable("status为false"));
            }
            CommonUtil.showToast("请求数据失败");
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}