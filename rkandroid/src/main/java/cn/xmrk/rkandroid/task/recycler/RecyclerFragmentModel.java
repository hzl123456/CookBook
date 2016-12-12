package cn.xmrk.rkandroid.task.recycler;

/**
 * 作者：请叫我百米冲刺 on 2016/10/25 09:22
 * 邮箱：mail@hezhilin.cc
 */

public class RecyclerFragmentModel {

    /**
     * 菜谱分级id
     **/
    private String id;

    /**
     * 加载的页码数
     **/
    private int page;

    /**
     * 加载的列数
     **/
    private int rows;


    public RecyclerFragmentModel(String id, int page, int rows) {
        this.id = id;
        this.page = page;
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}

