package cn.xmrk.rkandroid.task.recycler;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import cn.xmrk.rkandroid.adapter.HeaderFooterRecyclerViewAdapter;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 15:56
 * 邮箱：mail@hezhilin.cc
 */

public abstract class RecyclerAdapter<T> extends HeaderFooterRecyclerViewAdapter {


    public RecyclerAdapter(Fragment mFragment) {
        this.mFragment = mFragment;
        datas = new ArrayList<>();
    }

    protected RecyclerListener mCookBookListener;

    public void setCookBookListener(RecyclerListener mCookBookListener) {
        this.mCookBookListener = mCookBookListener;
    }

    private Fragment mFragment;

    public Fragment getmFragment() {
        return mFragment;
    }


    private boolean isLoading;


    private boolean isLoadmoreEnable = true;


    public boolean isLoading() {
        return isLoading;
    }


    public void setLoading(boolean loading) {
        isLoading = loading;
    }


    public boolean isLoadmoreEnable() {
        return isLoadmoreEnable;
    }


    public void setLoadmoreEnable(boolean loadmoreEnable) {
        isLoadmoreEnable = loadmoreEnable;
    }

    /**
     * 加载更多容器
     */
    private ViewSwitcher containerLoadmore;

    /**
     * 加载得到的数据
     **/
    private List<T> datas;


    public List<T> getDatas() {
        return datas;
    }


    public void setDatas(List<T> datas) {
        this.datas = datas;
    }


    public void showEndLoading() {
        if (containerLoadmore != null) {
            containerLoadmore.setDisplayedChild(0);
        }
    }

    public void showEndClickLoading() {
        if (containerLoadmore != null) {
            containerLoadmore.setDisplayedChild(1);
        }
    }


    @Override
    protected int getHeaderItemCount() {
        return 0;
    }

    @Override
    protected int getFooterItemCount() {
        return isLoadmoreEnable ? 1 : 0;
    }

    @Override
    protected int getContentItemCount() {
        return datas == null ? 0 : datas.size();
    }


    @Override
    protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return new LoadmoreViewHolder(View.inflate(parent.getContext(), cn.xmrk.rkandroid.R.layout.layout_loadmore, null));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        return onUserCreateContentItemViewHolder(parent, contentViewType);
    }

    @Override
    protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder, int position) {

    }


    public void removeFromParent() {
        if (containerLoadmore != null && containerLoadmore.getParent() != null) {
            ViewGroup _parent = (ViewGroup) containerLoadmore.getParent();
            _parent.removeView(containerLoadmore);
        }
    }

    @Override
    protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder, int position) {
        LoadmoreViewHolder _lHolder = (LoadmoreViewHolder) footerViewHolder;
        if (isLoadmoreEnable && !isLoading) {
            if (mCookBookListener != null) {
                mCookBookListener.onLoadData();
            }
        }
        containerLoadmore = _lHolder.vsLoadmore;
        // 显示的为加载中的
        if (isLoading) {
            showEndLoading();
        } else {
            showEndClickLoading();
        }
    }


    @Override
    protected void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position) {
        onUserBindContentItemViewHolder(contentViewHolder, position);
    }

    /**
     * 加载更多
     */
    private class LoadmoreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ViewSwitcher vsLoadmore;
        public final Button btnLoadmore;

        public LoadmoreViewHolder(View itemView) {
            super(itemView);

            vsLoadmore = (ViewSwitcher) itemView.findViewById(cn.xmrk.rkandroid.R.id.vs_loadmore);
            btnLoadmore = (Button) itemView.findViewById(cn.xmrk.rkandroid.R.id.btn_loadmore);

            btnLoadmore.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == btnLoadmore) {
                // 加载更多
                if (mCookBookListener != null) {
                    mCookBookListener.onLoadData();
                }
            }
        }
    }

    public abstract RecyclerView.ViewHolder onUserCreateContentItemViewHolder(ViewGroup parent, int contentViewType);

    public abstract void onUserBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position);
}
