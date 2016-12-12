package cn.xmrk.rkandroid.net.entity;

import java.io.File;

/**
 * 作者：请叫我百米冲刺 on 2016/12/12 上午9:18
 * 邮箱：mail@hezhilin.cc
 */

public class UploadFile {

    public String name;

    public File file;

    public UploadFile(String name, File file) {
        this.name = name;
        this.file = file;
    }

    public UploadFile(String name, String path) {
        this.name = name;
        this.file = new File(path);
    }
}
