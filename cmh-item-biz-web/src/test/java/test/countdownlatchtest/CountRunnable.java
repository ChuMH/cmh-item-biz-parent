package test.countdownlatchtest;

import java.util.concurrent.CountDownLatch;

/**
 * @author：
 * @data：
 * @description：
 */
public class CountRunnable implements Runnable{
    private static CountDownLatch countDownLatch;
    static {
        countDownLatch = new CountDownLatch(100);
    }
    @Override
    public void run() {
        try {
            synchronized (countDownLatch){
                countDownLatch.countDown();
                System.out.println("thread counts = "+(countDownLatch.getCount()));
            }
            countDownLatch.await();
            System.out.println("concurrency counts = "+(100-countDownLatch.getCount()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
