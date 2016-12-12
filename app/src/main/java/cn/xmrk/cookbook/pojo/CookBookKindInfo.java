package cn.xmrk.cookbook.pojo;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 15:38
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookKindInfo {


    /**
     * cookclass : 0
     * description : 保健养生
     * id : 15
     * keywords : 保健养生
     * name : 保健养生
     * seq : 0
     * title : 保健养生
     */

    private int cookclass;
    private String description;
    private int id;
    private String keywords;
    private String name;
    private int seq;
    private String title;

    public int getCookclass() {
        return cookclass;
    }

    public void setCookclass(int cookclass) {
        this.cookclass = cookclass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Type getListType() {
        return new TypeToken<List<CookBookKindInfo>>() {
        }.getType();
    }
}
