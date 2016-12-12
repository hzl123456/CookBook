package cn.xmrk.rkandroid.task.recycler;

import android.support.v7.widget.RecyclerView;

/**
 * 作者：请叫我百米冲刺 on 2016/10/25 14:16
 * 邮箱：mail@hezhilin.cc
 */

public interface RecyclerListener {

    void onLoadData();

    void onItemClick(RecyclerView.ViewHolder holder);
}
