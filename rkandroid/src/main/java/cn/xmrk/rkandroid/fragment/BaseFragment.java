package cn.xmrk.rkandroid.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import org.apache.log4j.Logger;

import cn.xmrk.rkandroid.activity.BaseActivity;
import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.config.RKConfigHelper;

/**
 * 2014年11月4日 下午3:59:50
 */
public abstract class BaseFragment extends RxFragment {

    private final Logger log = Logger.getLogger(getClass());

    private View contentView;

    protected boolean isShow = false;

    /**
     * 为true时，需要调用isShow，此参数用于防止 onCreateView 跟 dispatch 重复调用了 onShow() 方法
     */
    protected transient boolean isShowDispatch = false;

    public View getContentView() {
        return contentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dispatchHide();
        contentView = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dispatchShow();
    }

    public Toolbar getTitleBar() {
        return getBaseActivity().getTitlebar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (contentView == null) {
            try {
                contentView = inflater.inflate(getContentViewId(), null);
                if (RKConfigHelper.getInstance().getRKConfig().isDebug()) {
                    log.debug("[onCreateView] isShow == " + isShow + ", this == " + this);
                }
                initOnCreateView(true);
            } catch (InflateException e) {
                log.error("[onCreateView]", e);
            }
        } else {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null)
                parent.removeView(contentView);
            initOnCreateView(false);
        }
        if (isShowDispatch) {
            onShow();
        }
        return contentView;
    }

    /**
     * onCreateView中调用
     *
     * @param isCreate 表示为 onCreateView是首次打开
     */
    protected void initOnCreateView(boolean isCreate) {

    }


    public void startActivity(Class<? extends Activity> actCls) {
        Context context = getActivity();
        if (context == null) {
            context = RKApplication.getInstance();
        }
        startActivity(new Intent(context, actCls));
    }

    public void startActivityForResult(Class<? extends Activity> actCls, int requestCode) {
        try {
            Context context = getActivity();
            if (context == null) {
                context = RKApplication.getInstance();
            }
            startActivityForResult(new Intent(context, actCls), requestCode);
        } catch (Exception e) {
            log.error("[startActivityForResult]", e);
        }
    }


    public boolean isShow() {
        return isShow;
    }

    public void dispatchShow() {
        if (RKConfigHelper.getInstance().getRKConfig().isDebug()) {
            log.debug("[dispatchShow] isViewCreate == " + (contentView != null) + ", this == " + this);
        }
        if (!isShow) {
            if (contentView == null) {
                isShowDispatch = true;
            } else {
                onShow();
            }
        }
    }

    public void dispatchHide() {
        if (contentView != null && isShow) {
            onHide();
        }
    }

    public void onShow() {
        isShowDispatch = false;
        isShow = true;
    }

    public void onHide() {
        isShow = false;
    }


    public boolean onBackPressed() {
        return true;
    }


    public View findViewById(int id) {
        if (contentView == null) {
            return null;
        } else {
            return contentView.findViewById(id);
        }
    }

    public BaseActivity getBaseActivity() {
        Activity _activity = getActivity();
        if (_activity != null && _activity instanceof BaseActivity) {
            return (BaseActivity) _activity;
        } else {
            return null;
        }
    }

    /**
     * 获取到ContentView的id
     *
     * @return
     */
    protected abstract int getContentViewId();


    public boolean canBackActivity() {
        return true;
    }

}
