package cn.xmrk.cookbook.adapter;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.xmrk.cookbook.R;
import cn.xmrk.rkandroid.adapter.viewholder.BaseRecycleViewHolder;
import cn.xmrk.rkandroid.net.entity.DownState;
import cn.xmrk.rkandroid.net.entity.DownloadInfo;
import cn.xmrk.rkandroid.net.http.HttpDownManager;
import cn.xmrk.rkandroid.net.listener.HttpDownOnNextListener;
import cn.xmrk.rkandroid.utils.CommonUtil;
import cn.xmrk.rkandroid.utils.StringUtil;

/**
 * 作者：请叫我百米冲刺 on 2016/12/16 下午4:15
 * 邮箱：mail@hezhilin.cc
 */

public class DownloadHolder extends BaseRecycleViewHolder<DownloadInfo> implements View.OnClickListener {

    private HttpDownManager manager;

    private Button btn_start;
    private Button btn_pause;
    private TextView tv_name;
    private TextView tv_info;
    private TextView tv_progress;


    public DownloadHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    public void initData() {
        manager = HttpDownManager.getInstance();
        info.setListener(httpProgressOnNextListener);
        tv_name.setText(StringUtil.getFileName(info.getFilePath()));
        //设置状态显示
        if (info.getCountLength() > 0) {
            long percent = info.getReadLength() * 100 / info.getCountLength();
            tv_progress.setText(percent + "%");
        }
    }


    private void initView(View itemView) {
        btn_start = (Button) itemView.findViewById(R.id.btn_start);
        btn_pause = (Button) itemView.findViewById(R.id.btn_pause);
        tv_info = (TextView) itemView.findViewById(R.id.tv_info);
        tv_progress = (TextView) itemView.findViewById(R.id.tv_progress);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);

        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: //开始
                if (info.getState() != DownState.FINISH.getState()) {
                    manager.startDown(info);
                }
                break;
            case R.id.btn_pause: //暂停
                manager.pause(info);
                break;
        }

    }

    /*下载回调*/
    HttpDownOnNextListener<DownloadInfo> httpProgressOnNextListener = new HttpDownOnNextListener<DownloadInfo>() {
        @Override
        public void onNext(DownloadInfo baseDownEntity) {
            tv_info.setText("下载完成：");
            CommonUtil.showToast(baseDownEntity.getFilePath());
        }

        @Override
        public void onStart() {
            tv_info.setText("开始下载：");
        }

        @Override
        public void onComplete() {
            tv_info.setText("下载结束：");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            tv_info.setText("下载失败:" + e.toString());
        }


        @Override
        public void onPuase() {
            super.onPuase();
            tv_info.setText("下载暂停：");
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            tv_info.setText("提示:下载中");
            if (countLength > 0) {
                Log.i("id-->" + info.getId(), readLength * 100 / countLength + "%");
                tv_progress.setText(readLength * 100 / countLength + "%");
            }
        }
    };


}
