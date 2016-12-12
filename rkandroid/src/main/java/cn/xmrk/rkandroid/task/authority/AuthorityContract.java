package cn.xmrk.rkandroid.task.authority;


import android.support.annotation.NonNull;

import cn.xmrk.rkandroid.base.BaseFragActView;
import cn.xmrk.rkandroid.base.BasePresenter;

/**
 * 作者：请叫我百米冲刺 on 2016/10/27 10:34
 * 邮箱：mail@hezhilin.cc
 * <p>
 * 需要权限管理的Contract
 */

public interface AuthorityContract {

    interface View extends BaseFragActView<AuthorityContract.Presenter> {

        void loadAuthoritySuccess();

        void loadAuthorityFail();
    }

    interface Presenter extends BasePresenter {

        void insertDummyContactWrapper();

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

        void showVersion();

        void canUse();

    }
}
