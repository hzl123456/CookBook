package cn.xmrk.rkandroid.net.entity;

/**
 * 下载状态
 * Created by WZG on 2016/10/21.
 */

public enum DownState {
    ERROR(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    START(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownState(int state) {
        this.state = state;
    }
}
