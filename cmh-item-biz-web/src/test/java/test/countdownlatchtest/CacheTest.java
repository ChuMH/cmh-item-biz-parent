package test.countdownlatchtest;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheTest {
    @Test
    public void testLoad() throws Exception{
        LoadingCache<String,Object> loadingCache=CacheBuilder.newBuilder().build(new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) throws Exception {
                System.out.println("load value for key:"+key);
                return key+"";
            }
        });
        //第一次查询会执行cache的load方法,第二次直接从缓存获取
        System.out.println(loadingCache.get("key1"));
        System.out.println(loadingCache.get("key1"));
    }

    @Test
    public void testTimeBasedEviction() throws Exception{
        //写入30秒后过期，并监听删除事件，一旦有缓存数据被删除则会通知监听
        Cache<String,Object> cache= CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS)
                //为了查看过期效果，这里讲并发数改为了1？
                .concurrencyLevel(1)
                .removalListener(notification -> {
                    System.out.println(notification.getValue() +
                            " is removed at:"+System.currentTimeMillis()/1000);
                }).build();
        //每隔10十秒一个对象，共放入5个对象,放入第4个对象时因为第1个已经过期则会删除第0个对象
        for(int i=0;i<=4;i++){
            System.out.println("put key"+i+":"+i+" at:"+System.currentTimeMillis()/1000);
            cache.put("key" + i, i);
            Thread.sleep(12000);
        }
    }
}
