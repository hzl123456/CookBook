package cn.xmrk.rkandroid.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import org.apache.log4j.Logger;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.net.entity.DownState;
import cn.xmrk.rkandroid.net.entity.DownloadInfo;
import cn.xmrk.rkandroid.utils.RxBus;

/**
 * Created by Au61 on 2016/5/3.
 */
public class DownloadDb {

    private static final Logger log = Logger.getLogger("DownloadInfo");

    private OpenHelper mOpenHelper;

    private static DownloadDb instance;

    private DownloadDb() {
        open(RKApplication.getInstance(), "download");
    }

    public static DownloadDb getInstance() {
        synchronized (DownloadDb.class) {
            if (instance == null) {
                instance = new DownloadDb();
            }
        }
        return instance;
    }

    public void open(Context context, String dbKey) {
        if (mOpenHelper == null || !mOpenHelper.isOpen()) {
            mOpenHelper = OpenHelper.getInstance(context, dbKey);
        }
    }

    public void close() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
        }

    }


    public Dao getDownloadInfoDao() {
        return mOpenHelper.getDownloadInfoDao();
    }


    /**
     * 保存或者更新数据
     *
     * @param info
     */
    public Dao.CreateOrUpdateStatus saveUpdateDownloadInfo(DownloadInfo info) {
        //TODO 同时通过rx进行发送出来
        RxBus.getDefault().post(info);
        Dao _dao = getDownloadInfoDao();
        try {
            return _dao.createOrUpdate(info);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Dao.CreateOrUpdateStatus(false, false, 0);
        }
    }

    /**
     * 删除数据
     *
     * @param info
     */
    public void deleteDownloadInfo(DownloadInfo info) {
        Dao _dao = getDownloadInfoDao();
        DeleteBuilder<DownloadInfo, Integer> _db = _dao.deleteBuilder();
        Where<DownloadInfo, Integer> _where = _db.where();
        try {
            _where.eq("id", info.getId());
            _db.delete();
        } catch (SQLException e) {
            log.error("数据删除失败", e);
        }
    }

    /**
     * 获取下载数据，按照状态进行排序
     * <p>
     * start的排在最前面
     *
     * @param
     */
    public List<DownloadInfo> getDownloadInfo() {
        Dao _dao = getDownloadInfoDao();
        QueryBuilder<DownloadInfo, Integer> _qb = _dao.queryBuilder();
        //按照状态由小到大排序
        _qb.orderBy("state", true);
        try {
            //TODO 对文件进行状态的检查,主要检查文件是否存在
            List<DownloadInfo> mDatas = _qb.query();
            DownloadInfo data = null;
            File file = null;
            for (int i = 0; i < mDatas.size(); i++) {
                data = mDatas.get(i);
                file = new File(data.getFilePath());
                if (!file.isFile()) {//证明已经被删除了，要进行更新
                    data.setState(DownState.START.getState());
                    data.setReadLength(0);
                    saveUpdateDownloadInfo(data);
                }
            }
            return mDatas;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
