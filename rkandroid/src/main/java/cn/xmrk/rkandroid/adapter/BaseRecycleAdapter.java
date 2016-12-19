package cn.xmrk.rkandroid.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.xmrk.rkandroid.adapter.listener.OnViewHolderClickListener;
import cn.xmrk.rkandroid.adapter.viewholder.BaseRecycleViewHolder;

/**
 * Created by Au61 on 2016/6/15.
 */
public abstract class BaseRecycleAdapter<T, D extends BaseRecycleViewHolder> extends RecyclerView.Adapter<D> {

    protected OnViewHolderClickListener mOnViewHolderClickListener;

    public void setOnViewHolderClickListener(OnViewHolderClickListener mOnViewHolderClickListener) {
        this.mOnViewHolderClickListener = mOnViewHolderClickListener;
    }

    protected List<T> mData;

    public BaseRecycleAdapter(List<T> mData) {
        this.mData = mData;
    }

    public List<T> getmData() {
        return mData;
    }

    public void setmData(List<T> mData) {
        this.mData = mData;
    }

    public void remove(List<T> removeData) {
        this.mData.removeAll(removeData);
    }

    public void remove(T removeData) {
        this.mData.remove(removeData);
    }

    public void add(T addData) {
        this.mData.add(addData);
    }

    public void add(List<T> addData) {
        this.mData.addAll(addData);
    }

    @Override
    public void onBindViewHolder(D viewHolder, int position) {
        viewHolder.info = mData.get(position);
        viewHolder.position = position;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

}
