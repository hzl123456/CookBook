package cn.xmrk.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.xmrk.cookbook.R;
import cn.xmrk.rkandroid.adapter.BaseRecycleAdapter;
import cn.xmrk.rkandroid.net.entity.DownloadInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/12/16 下午2:26
 * 邮箱：mail@hezhilin.cc
 */

public class DownloadAdapter extends BaseRecycleAdapter<DownloadInfo, DownloadHolder> {


    public DownloadAdapter(List<DownloadInfo> mData) {
        super(mData);
    }

    @Override
    public DownloadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadHolder(View.inflate(parent.getContext(), R.layout.item_download, null));
    }

    @Override
    public void onBindViewHolder(DownloadHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        viewHolder.initData();
    }

}
