package cn.xmrk.rkandroid.adapter.listener;

import android.support.v7.widget.RecyclerView;

/**
 * ViewHolder的根View被点击
 */
public interface OnViewHolderClickListener {

    void OnViewHolderClick(RecyclerView.ViewHolder holder);

    void OnViewHolderLongClick(RecyclerView.ViewHolder holder);

    void OnViewHolderRemove(RecyclerView.ViewHolder holder);

}
