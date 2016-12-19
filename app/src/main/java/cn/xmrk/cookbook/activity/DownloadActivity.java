package cn.xmrk.cookbook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.xmrk.cookbook.R;
import cn.xmrk.cookbook.adapter.DownloadAdapter;
import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.rkandroid.db.DownloadDb;
import cn.xmrk.rkandroid.net.entity.DownloadInfo;
import cn.xmrk.rkandroid.utils.CommonUtil;

/**
 * 作者：请叫我百米冲刺 on 2016/12/16 上午11:46
 * 邮箱：mail@hezhilin.cc
 */

public class DownloadActivity extends BackableBaseActivity {

    private RecyclerView rv_content;

    private DownloadAdapter mAdapter;

    private LinearLayoutManager layoutManager;

    private List<DownloadInfo> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        setToolbarNeedToScrollow(false);
        getDownloadInfos();
        initView();
    }


    private void initView() {
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        mAdapter = new DownloadAdapter(mDatas);
        layoutManager = new LinearLayoutManager(this);

        rv_content.setLayoutManager(layoutManager);
        rv_content.setAdapter(mAdapter);
    }

    private void getDownloadInfos() {
        mDatas = DownloadDb.getInstance().getDownloadInfo();
        if (mDatas.isEmpty()) {
            mDatas = new ArrayList<DownloadInfo>();
            String[] urls = {
                    "http://www.izaodao.com/app/izaodao_app.apk",
                    "http://www.izaodao.com/app/izaodao_app.apk",
                    "http://www.izaodao.com/app/izaodao_app.apk",
                    "http://www.izaodao.com/app/izaodao_app.apk",
                    "http://www.izaodao.com/app/izaodao_app.apk"
            };
            DownloadInfo info = null;
            String outputPath = null;
            for (int i = 0; i < urls.length; i++) {
                outputPath = CommonUtil.getDir() + File.separator + "test" + i + ".apk";
                info = new DownloadInfo(urls[i], outputPath);
                mDatas.add(info);
                //数据库进行保存
                DownloadDb.getInstance().saveUpdateDownloadInfo(info);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*记录退出时下载任务的状态-复原用*/
        if (mDatas != null && mDatas.size() > 0) {
            for (DownloadInfo downInfo : mDatas) {
                DownloadDb.getInstance().saveUpdateDownloadInfo(downInfo);
            }
        }
    }
}
