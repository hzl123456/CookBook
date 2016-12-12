package cn.xmrk.rkandroid.adapter.listener;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;


/**
 * 创建日期： 2015/11/12.
 * RecyclerView版PauseOnScrollListener，直接照抄UIL PauseOnScrollListener的代码
 */
public class RecyclerViewPauseOnScrollListener extends RecyclerView.OnScrollListener {

    private final boolean pauseOnScroll;
    private final boolean pauseOnFling;
    private final RecyclerView.OnScrollListener externalListener;
    private WeakReference<Context> mContext;

    public RecyclerViewPauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling, Context context) {
        this(pauseOnScroll, pauseOnFling, context, null);
    }

    public RecyclerViewPauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling, Context context, RecyclerView.OnScrollListener customListener) {
        this.pauseOnScroll = pauseOnScroll;
        this.pauseOnFling = pauseOnFling;
        this.externalListener = customListener;
        this.mContext = new WeakReference<Context>(context);
    }

    @Override
    public void onScrollStateChanged(RecyclerView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        switch (scrollState) {
            case 0:
                Glide.with(view.getContext()).resumeRequests();
                break;
            case 1:
                if (this.pauseOnScroll) {
                    Glide.with(mContext.get()).pauseRequests();
                }
                break;
            case 2:
                if (this.pauseOnFling) {
                    Glide.with(mContext.get()).pauseRequests();
                }
        }

        if (this.externalListener != null) {
            this.externalListener.onScrollStateChanged(view, scrollState);
        }

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (this.externalListener != null) {
            this.externalListener.onScrolled(recyclerView, dx, dy);
        }
    }

}
