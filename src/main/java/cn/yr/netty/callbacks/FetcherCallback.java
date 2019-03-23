package cn.yr.netty.callbacks;



/**
 * @Auther: linxinmin
 * @Date: 2018/11/15 09:30
 * @Description:
 */
public interface FetcherCallback {
    void onData(Data date) throws Exception;
    void onError(Throwable cause);
}
