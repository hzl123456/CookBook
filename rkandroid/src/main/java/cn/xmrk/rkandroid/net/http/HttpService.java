package cn.xmrk.rkandroid.net.http;


import cn.xmrk.rkandroid.net.entity.BaseResultEntity;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * service统一接口数据
 * Created by WZG on 2016/7/16.
 */
public interface HttpService {


    /**
     * 获取菜谱列表
     * @param  id 菜谱id
     * @param page 页码 从1开始
     * @param rows 每页个数。默认为20
     * **/
    @POST("list")
    Observable<BaseResultEntity> getCookBookList(@Query("id") String id,@Query("page") int page,@Query("rows") int rows);

    /**
     * 获取菜谱分类
     * @param  id 上级菜谱分类
     * **/
    @POST("classify")
    Observable<BaseResultEntity> getCookBookKinds(@Query("id") String id);


    /*断点续传下载接口*/
    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> downloadFile(@Header("RANGE") String start, @Url String url);

}
