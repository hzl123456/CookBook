package cn.xmrk.rkandroid.task.recycler;

import java.util.List;

import cn.xmrk.rkandroid.base.BaseFragActView;
import cn.xmrk.rkandroid.base.BasePresenter;

/**
 * 作者：请叫我百米冲刺 on 2016/10/25 09:16
 * 邮箱：mail@hezhilin.cc
 */

public interface RecyclerFragmentContract {


    interface View extends BaseFragActView<Presenter> {

        /**
         * 显示加载无数据
         **/
        void showNullData();

        /**
         * 显示有数据加载
         **/
        void showHasLoadData();

        /**
         * 隐藏下拉刷新的显示
         **/
        void dMRefreshLoading();


        RecyclerAdapter getRecyclerAdapter();

    }

    interface Presenter extends BasePresenter {

        /**
         * 加载列表数据，这边需要页码和列数
         **/
        void loadRecyclerInfo();

        /**
         * 有数据的时候进行的更新
         **/
        void notifyAdapter(int page, List datas);

        /**
         * 无数据的时候进行的更新，主要是更新状态
         **/
        void notifyAdapter();

        /**
         * 进行数据的刷新
         **/
        void refreshDatas();

    }

}
