package cn.xmrk.rkandroid.net.entity;

import com.google.gson.JsonElement;

/**
 * 回调信息统一封装类
 * Created by WZG on 2016/7/16.
 */
public class BaseResultEntity {

    /**
     * success为操作成功，其他为失败 必有参数
     */
    public boolean status;
    /**
     * 数据的集合
     */
    public JsonElement tngou;


    public boolean isStatus() {
        return status;
    }

    public JsonElement getTngou() {
        return tngou;
    }
}
