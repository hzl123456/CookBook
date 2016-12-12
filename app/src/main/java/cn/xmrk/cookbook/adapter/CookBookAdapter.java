package cn.xmrk.cookbook.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xmrk.cookbook.R;
import cn.xmrk.cookbook.pojo.CookBookInfo;
import cn.xmrk.rkandroid.task.recycler.RecyclerAdapter;
import cn.xmrk.rkandroid.utils.RKUtil;
import cn.xmrk.rkandroid.utils.StringUtil;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 15:56
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookAdapter extends RecyclerAdapter<CookBookInfo> {


    public CookBookAdapter(Fragment mFragment) {
        super(mFragment);
    }

    @Override
    public RecyclerView.ViewHolder onUserCreateContentItemViewHolder(ViewGroup parent, int contentViewType) {
        return new ContentViewHolder(View.inflate(parent.getContext(), R.layout.item_cookbook, null));
    }

    @Override
    public void onUserBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder, int position) {
        ContentViewHolder holder = (ContentViewHolder) contentViewHolder;
        final CookBookInfo info = getDatas().get(position);

        holder.info = info;
        holder.tv_name.setText(getmFragment().getString(R.string.cookbookname, info.getName()));

        if (!StringUtil.isEmptyString(info.getImg())) {
            RKUtil.displayImage(info.getImg(), holder.img_cookbook, R.drawable.bg_caip, getmFragment());
        }
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tv_name;
        public final ImageView img_cookbook;

        public CookBookInfo info;

        public ContentViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            img_cookbook = (ImageView) itemView.findViewById(R.id.img_cookbook);

            itemView.findViewById(R.id.layout_containert).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // 加载更多
            if (mCookBookListener != null) {
                mCookBookListener.onItemClick(this);
            }
        }
    }

}
