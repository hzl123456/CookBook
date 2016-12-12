package cn.xmrk.cookbook.callback;

import android.support.v7.util.DiffUtil;

import java.util.List;

import cn.xmrk.cookbook.pojo.CookBookInfo;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 15:50
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookCallback extends DiffUtil.Callback {

    private List<CookBookInfo> oldList;
    private List<CookBookInfo> newList;

    public CookBookCallback(List<CookBookInfo> oldList, List<CookBookInfo> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getDescription().equals(newList.get(newItemPosition).getDescription());
    }
}
