package cn.xmrk.cookbook.fragment.cookbooklist;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import cn.xmrk.cookbook.pojo.CookBookInfo;
import cn.xmrk.rkandroid.task.recycler.RecyclerFragmentContract;
import cn.xmrk.rkandroid.task.recycler.RecyclerFragmentPresenter;
import cn.xmrk.rkandroid.net.entity.BaseResultEntity;
import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.utils.CommonUtil;
import rx.Observable;

/**
 * 作者：请叫我百米冲刺 on 2016/10/25 09:24
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookFragmentPresenter extends RecyclerFragmentPresenter<CookBookInfo> {


    public CookBookFragmentPresenter(@NonNull RecyclerFragmentContract.View mView, boolean isCreate, String id) {
        super(mView, isCreate, id);
    }

    @Override
    public Observable getUserObservable(HttpService methods) {
        return methods.getCookBookList(model.getId(), model.getPage(), model.getRows());
    }


    @Override
    public ArrayList<CookBookInfo> getDataList(BaseResultEntity result) {
        return CommonUtil.getGson().fromJson(result.getTngou(), CookBookInfo.getListType());
    }

}
