package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author：
 * @data：
 * @description：Atomic原子性试验
 */
public class ThreadTest {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static int i = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newWorkStealingPool();
        for(int j = 0;j < 1000 ;j++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello world!" + (i++));
                }
            });
        }
    }
}
