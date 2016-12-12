package cn.xmrk.rkandroid.task.recycler;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.xmrk.rkandroid.R;
import cn.xmrk.rkandroid.adapter.listener.RecyclerViewPauseOnScrollListener;
import cn.xmrk.rkandroid.fragment.BaseFragment;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.decoration.SpacesItemDecoration;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 17:19
 * 邮箱：mail@hezhilin.cc
 */
public abstract class RecyclerViewFragment<T> extends BaseFragment implements RecyclerFragmentContract.View, SwipeRefreshLayout.OnRefreshListener, RecyclerListener {

    private RecyclerView rv_content;
    private TextView tv_empty;
    private ScrollView rv_scrollow;
    private SwipeRefreshLayout sf_refresh;

    private RecyclerAdapter mAdapter;
    private RecyclerFragmentContract.Presenter mPresenter;

    @Override
    public RecyclerAdapter getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initOnCreateView(boolean isCreate) {
        super.initOnCreateView(isCreate);
        if (isCreate) {
            //实例化presenter
            getRecyclerFragmentPresenter(this, isCreate, getArguments().getString("id"));
        }
        if (mAdapter != null) {
            mAdapter.removeFromParent();
        }
    }


    public abstract RecyclerFragmentPresenter getRecyclerFragmentPresenter(@NonNull RecyclerFragmentContract.View mView, boolean isCreate, String id);

    public abstract RecyclerAdapter<T> getUserRecyclerAdapter(Fragment fragment);

    public abstract RecyclerView.LayoutManager getLayoutManager();


    @Override
    public void findViews() {
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        sf_refresh = (SwipeRefreshLayout) findViewById(R.id.sf_refresh);
        rv_scrollow = (ScrollView) findViewById(R.id.rv_scrollow);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        sf_refresh.setOnRefreshListener(this);
        sf_refresh.setColorSchemeResources(R.color.bg_title_bar);
        //设置滚动不加载图片
        rv_content.setOnScrollListener(new RecyclerViewPauseOnScrollListener(true, true, getActivity()));
    }

    @Override
    public void initViews() {
        mAdapter = getUserRecyclerAdapter(this);
        mAdapter.setCookBookListener(this);

        rv_content.setLayoutManager(getLayoutManager());
        rv_content.setAdapter(mAdapter);
        //设置为空时候的图片显示
        tv_empty.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_launcher, 0, 0);

        int space = CommonUtil.dip2px(5);
        rv_content.addItemDecoration(new SpacesItemDecoration(space, space));
    }


    @Override
    public void onRefresh() {
        mPresenter.refreshDatas();
    }


    @Override
    public void showNullData() {
        rv_content.setVisibility(View.GONE);
        rv_scrollow.setVisibility(View.VISIBLE);
    }

    @Override
    public void showHasLoadData() {
        rv_content.setVisibility(View.VISIBLE);
        rv_scrollow.setVisibility(View.GONE);
    }

    @Override
    public void dMRefreshLoading() {
        if (sf_refresh.isRefreshing()) {
            sf_refresh.setRefreshing(false);
        }
    }

    @Override
    public void setPresenter(RecyclerFragmentContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void onLoadData() {
        mPresenter.loadRecyclerInfo();
    }

}
