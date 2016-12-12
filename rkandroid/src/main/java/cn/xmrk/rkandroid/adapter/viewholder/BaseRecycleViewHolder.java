package cn.xmrk.rkandroid.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.xmrk.rkandroid.adapter.listener.OnViewHolderClickListener;

/**
 * Created by Au61 on 2016/7/12.
 */
public class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder {

    public int position;

    public T info;

    public OnViewHolderClickListener mOnViewHolderClickListener;

    public void setOnViewHolderClickListener(OnViewHolderClickListener mOnViewHolderClickListener) {
        this.mOnViewHolderClickListener = mOnViewHolderClickListener;
    }

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }
}
