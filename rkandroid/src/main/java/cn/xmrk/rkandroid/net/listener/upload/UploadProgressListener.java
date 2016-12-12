package cn.xmrk.rkandroid.net.listener.upload;

/**
 * 上传进度回调类
 * Created by WZG on 2016/10/20.
 */

public interface UploadProgressListener {
    /**
     * 上传进度
     *
     * @param fileName 文件名称
     * @param currentBytesCount
     * @param totalBytesCount
     */
    void onProgress(String fileName, long currentBytesCount, long totalBytesCount);
}