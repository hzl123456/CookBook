package cn.xmrk.cookbook.application;

import cn.xmrk.rkandroid.application.RKApplication;
import cn.xmrk.rkandroid.config.IRKConfig;

/**
 * 作者：请叫我百米冲刺 on 2016/10/24 14:48
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookApplication extends RKApplication {
    @Override
    protected IRKConfig getRKConfig() {
        return new IRKConfig() {
            @Override
            public boolean isDebug() {
                return true;
            }

            @Override
            public String getBaseUrl() {
                return "http://apis.baidu.com/tngou/cook/";
            }

            @Override
            public String getImageUrl() {
                return "http://tnfs.tngou.net/image";
            }
        };
    }

}
