package cn.yr.netty.callbacks;



/**
 * @Auther: linxinmin
 * @Date: 2018/11/15 09:33
 * @Description:
 */
public class MyFetcher implements Fetcher {
    final  Data data;

    public MyFetcher(Data data) {
        this.data = data;
    }

    public void fetchDate(FetcherCallback callback) {
        try {
            callback.onData(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
