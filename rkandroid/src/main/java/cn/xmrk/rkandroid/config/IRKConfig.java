package cn.xmrk.rkandroid.config;

/**
 * 创建日期： 2016/1/26.
 */
public interface IRKConfig {

    /**
     * 测试模式
     */
    boolean isDebug();

    /**
     * 网站域名
     */
    String getBaseUrl();

    /**
     * 图片地址的前缀
     **/
    String getImageUrl();

}
