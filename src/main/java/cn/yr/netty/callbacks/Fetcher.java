package cn.yr.netty.callbacks;

/**
 * @Auther: linxinmin
 * @Date: 2018/11/15 09:29
 * @Description:
 */
public interface Fetcher {
    void fetchDate(FetcherCallback callback);
}
