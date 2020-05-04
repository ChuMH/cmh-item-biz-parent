package test.countdownlatchtest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author：
 * @data：
 * @description：
 */
public class Parallellimit {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < 100;i++){
            CountRunnable runnable = new CountRunnable();
            executorService.execute(runnable);
        }
    }
}
