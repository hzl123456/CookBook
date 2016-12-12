package cn.xmrk.cookbook.fragment.cookbooklist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import cn.xmrk.cookbook.activity.CookBookDetailActivity;
import cn.xmrk.cookbook.adapter.CookBookAdapter;
import cn.xmrk.cookbook.pojo.CookBookInfo;
import cn.xmrk.rkandroid.task.recycler.RecyclerAdapter;
import cn.xmrk.rkandroid.task.recycler.RecyclerFragmentContract;
import cn.xmrk.rkandroid.task.recycler.RecyclerFragmentPresenter;
import cn.xmrk.rkandroid.task.recycler.RecyclerViewFragment;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 17:19
 * 邮箱：mail@hezhilin.cc
 */
public class CookBookFragment extends RecyclerViewFragment<CookBookInfo> {

    /**
     * 注意，这个是必须的，因为这边需要一个id
     **/
    public static CookBookFragment newInstance(String id) {
        CookBookFragment f = new CookBookFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        f.setArguments(args);
        return f;
    }

    @Override
    public RecyclerFragmentPresenter getRecyclerFragmentPresenter(@NonNull RecyclerFragmentContract.View mView, boolean isCreate, String id) {
        return new CookBookFragmentPresenter(mView, isCreate, id);
    }

    @Override
    public RecyclerAdapter<CookBookInfo> getUserRecyclerAdapter(Fragment fragment) {
        return new CookBookAdapter(fragment);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1);
    }


    @Override
    public void onItemClick(RecyclerView.ViewHolder holder) {
        if (holder instanceof CookBookAdapter.ContentViewHolder) {
            CookBookAdapter.ContentViewHolder viewHolder = (CookBookAdapter.ContentViewHolder) holder;
            CookBookDetailActivity.StartCookBookDetailActivity(getBaseActivity(), viewHolder.img_cookbook, viewHolder.info);
        }
    }
}
