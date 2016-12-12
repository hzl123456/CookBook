package cn.xmrk.rkandroid.task.authority;

/**
 * 作者：请叫我百米冲刺 on 2016/10/27 10:38
 * 邮箱：mail@hezhilin.cc
 */

public class AuthorityModel {

    private final int requestCode = 100;

    private String[] needPermission;


    public AuthorityModel(String[] needPermission) {
        this.needPermission = needPermission;
    }


    public String[] getNeedPermission() {
        return needPermission;
    }

    public void setNeedPermission(String[] needPermission) {
        this.needPermission = needPermission;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
