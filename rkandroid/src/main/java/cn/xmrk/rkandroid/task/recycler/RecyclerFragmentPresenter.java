package cn.xmrk.rkandroid.task.recycler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.net.entity.BaseResultEntity;
import cn.xmrk.rkandroid.net.entity.SubjectPostApi;
import cn.xmrk.rkandroid.net.http.HttpManager;
import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpOnNextListener;
import rx.Observable;

/**
 * 作者：请叫我百米冲刺 on 2016/10/25 09:24
 * 邮箱：mail@hezhilin.cc
 */

public abstract class RecyclerFragmentPresenter<T> implements RecyclerFragmentContract.Presenter {

    @Nullable
    protected RecyclerFragmentContract.View mView;
    protected RecyclerFragmentModel model;
    protected RecyclerAdapter<T> mAdapter;
    protected WeakReference<RxAppCompatActivity> mActivity;

    public RecyclerFragmentPresenter(@NonNull RecyclerFragmentContract.View mView, boolean isCreate, String id) {
        this.mView = mView;
        this.model = new RecyclerFragmentModel(id, 1, 10);
        //这两者使用同一个presenter，因为这两者的业务逻辑是一起的
        this.mView.setPresenter(this);

        if (mView instanceof BaseFragment) {
            this.mActivity = new WeakReference<RxAppCompatActivity>(((BaseFragment) mView).getBaseActivity());
            mView.findViews();
            mView.initViews();
            mAdapter = mView.getRecyclerAdapter();
        }
    }


    @Override
    public void loadRecyclerInfo() {
        //判断是否有更多的数据需要加载
        if (!mAdapter.isLoadmoreEnable()) {
            return;
        }
        mAdapter.setLoading(true);
        SubjectPostApi subject = new SubjectPostApi(new HttpOnNextListener() {
            @Override
            public void onNext(BaseResultEntity result) {
                ArrayList<T> datas = getDataList(result);
                if (datas == null || datas.size() == 0) {//此时表示没有更多数据了
                    if (model.getPage() == 1) {
                        mView.showNullData();
                    }
                    //设置是否可以加载更多和加载状态
                    mAdapter.setLoadmoreEnable(true);
                    mAdapter.setLoading(false);
                    //进行状态和数据的刷新
                    notifyAdapter();
                    return;
                }
                //设置是否可以加载更多和加载状态
                mAdapter.setLoadmoreEnable(true);
                mAdapter.setLoading(false);
                //根据数据去进行更新操作
                notifyAdapter(model.getPage(), datas);
                //page+1
                model.setPage(model.getPage() + 1);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //加载错误根据现有的数据进行对应的显示
                if (mAdapter.getDatas() == null || mAdapter.getDatas().size() == 0) {
                    mView.showNullData();
                } else {
                    mView.showHasLoadData();
                    mAdapter.showEndClickLoading();
                }
                notifyAdapter();
            }
        }, mActivity.get(), false) {
            @Override
            public Observable getObservable(HttpService methods) {
                return getUserObservable(methods);
            }
        };
        HttpManager.getInstance().doHttpDeal(subject);
    }


    public abstract Observable getUserObservable(HttpService methods);

    public abstract ArrayList<T> getDataList(BaseResultEntity result);

    @Override
    public void notifyAdapter(int page, @NonNull List datas) {
        mView.showHasLoadData();
        if (page == 1) {
            mAdapter.getDatas().clear();
        }
        mAdapter.getDatas().addAll(datas);
        notifyAdapter();
    }

    @Override
    public void notifyAdapter() {
        //更新完成，隐藏加载的loading
        mView.dMRefreshLoading();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshDatas() {
        //刷新的时候设置页码数为1
        model.setPage(1);
        loadRecyclerInfo();
    }

    @Override
    public void start() {

    }
}
