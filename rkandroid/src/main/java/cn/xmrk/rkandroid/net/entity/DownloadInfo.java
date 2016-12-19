package cn.xmrk.rkandroid.net.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.xmrk.rkandroid.net.http.HttpService;
import cn.xmrk.rkandroid.net.listener.HttpDownOnNextListener;

/**
 * 作者：请叫我百米冲刺 on 2016/12/16 下午1:59
 * 邮箱：mail@hezhilin.cc
 */

@DatabaseTable(tableName = "Download")
public class DownloadInfo {

    @DatabaseField(columnName = "id", generatedId = true)
    private long id;

    /*文件的存储位置*/
    @DatabaseField(columnName = "filePath")
    private String filePath;

    /*文件下载的url链接*/
    @DatabaseField(columnName = "url")
    private String url;

    /*文件总长度*/
    @DatabaseField(columnName = "countLength")
    private long countLength;

    /*文件下载长度*/
    @DatabaseField(columnName = "readLength")
    private long readLength;

    /*文件下载状态*/
    @DatabaseField(columnName = "state")
    private int state;


    /**
     * 下载的回调监听
     **/
    private HttpDownOnNextListener listener;

    /**
     * 下载的service
     **/
    private HttpService service;

    public DownloadInfo(){

    }


    public DownloadInfo(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    public HttpService getService() {
        return service;
    }

    public void setService(HttpService service) {
        this.service = service;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public HttpDownOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownOnNextListener listener) {
        this.listener = listener;
    }
}
