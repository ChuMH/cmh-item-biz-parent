package test.template.test;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author：
 * @data：
 * @description：
 */
public class SyncDeductions extends AbstractQueuedSynchronizer {
    /**
     * 银行每天最多发放的最大排队号
     */
    private static final Integer MAX_NO = 1000;
    /**
     * 当前最大排队号
     */
    private static volatile Integer no;

    static{
        no=0;
    }
    /**
     * 取号
     */
    public final Integer getNo() {
        acquire(1);
        if(no<MAX_NO) {
            no++;
            release(1);
            return no;
        }
        return null;
    }
    /**
     * 重写获取资源方法
     * @param acquires
     * @return
     */
    protected final boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        return false;
    }
    /**
     * 取号成功释放资源
     * @param releases
     * @return
     */
    protected final boolean tryRelease(int releases) {
        int c = getState() - releases;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }
}
