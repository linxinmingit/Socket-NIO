package cn.yr.netty.callbacks;

import java.util.logging.Logger;

/**
 * @Auther: linxinmin
 * @Date: 2018/11/15 09:26
 * @Description:
 */
public class Worker {
    private Logger logger = Logger.getLogger("Worker");
    public void doWork()
    {
        Fetcher fetcher = new MyFetcher(new Data(1,0));
        int c = 8/0;
        fetcher.fetchDate(new FetcherCallback() {
            public void onData(Data date) throws Exception { //��������ʱ���ã�
                //System.out.println("Data received: "+date);
                logger.info("Data received: "+date);
            }

            public void onError(Throwable cause) { //��������ʱ���ã�
                logger.info("An error accour: "+cause.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        //
        Worker w = new Worker();
        w.doWork();
    }
}
