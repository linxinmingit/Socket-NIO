package cn.yr.netty.Futures;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Auther: linxinmin
 * @Date: 2018/11/16 09:29
 * @Description:
 */
public class FutureExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable task1 = new Runnable() {
            public void run() {
                System.out.println("I am task1");
            }
        };
        Callable<Integer> task2 = new Callable<Integer>() {
            public Integer call() throws Exception {
                return new Integer(100);
            }
        };
        Future<?> f1 = (Future<?>) executor.submit(task2);
        Future<Integer> f2 = (Future<Integer>) executor.submit(task2);
        System.out.println("task1 is completed£¿ "+f1.isDone());
        System.out.println("task2 is completed£¿ "+f2.isDone());
        while (f1.isDone())
        {
            System.out.println("task1 completed!");
            break;
        }
        while (f2.isDone())
        {
            try {
                System.out.println("return value by task2£º "+f2.get());
                break;
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
