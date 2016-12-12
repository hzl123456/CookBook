package cn.xmrk.rkandroid.task.authority;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：请叫我百米冲刺 on 2016/10/27 10:38
 * 邮箱：mail@hezhilin.cc
 */

public class AuthorityPresenter implements AuthorityContract.Presenter {

    private Activity mActivity;
    private AuthorityModel model;
    private AuthorityContract.View mView;

    public AuthorityPresenter(@NonNull AuthorityContract.View mView, String[] needPermission) {
        this.mView = mView;
        this.mView.setPresenter(this);

        if (mView instanceof Activity) {
            this.mActivity = (Activity) mView;
            model = new AuthorityModel(needPermission);
            showVersion();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void insertDummyContactWrapper() {
        //需要手动请求的权限，（相机，音频，文件读写,定位）
        String[] needPermission = model.getNeedPermission();
        //未允许使用的权限
        List<String> needToPer = new ArrayList<>();
        for (int i = 0; i < needPermission.length; i++) {
            int hasWriteContactsPermission = mActivity.checkSelfPermission(needPermission[i]);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                needToPer.add(needPermission[i]);
            }
        }
        if (needToPer.size() == 0) {
            canUse();
        } else {
            String[] sp = new String[needToPer.size()];
            for (int i = 0; i < sp.length; i++) {
                sp[i] = needToPer.get(i);
            }
            mActivity.requestPermissions(sp,
                    model.getRequestCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == model.getRequestCode()) {//权限结果来了
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    mView.loadAuthorityFail();
                    new AlertDialog.Builder(mActivity)
                            .setMessage("有权限未被允许使用，可在安全中心-权限管理中打开权限").setCancelable(false)
                            .setPositiveButton("退出CookBook", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mActivity.finish();
                                }
                            })
                            .create()
                            .show();
                    return;
                }
            }
            canUse();
        }
    }

    @Override
    public void showVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//当前的sdk版本大于等于23
            insertDummyContactWrapper();
        } else {
            canUse();
        }
    }

    @Override
    public void canUse() {
        mView.findViews();
        mView.initViews();
        mView.loadAuthoritySuccess();
    }

    @Override
    public void start() {

    }
}
