package com.cmh.item.biz.dao.es;


import com.cmh.item.biz.dao.es.config.EsClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;

@Slf4j
@Configuration
public class EsClientConfig {

    @Resource
    private EsClientProperties esClientProperties;

    /**
     * 避免初始化失败
     */
    @PostConstruct
    public void init(){
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Bean("esClient")
    public Client getClient(){
        try {
            Settings settings = Settings.builder()
                    .put("client.transport.sniff", false)
                    .put("cluster.name", esClientProperties.getClusterName())
                    .build();
            TransportClient client = new PreBuiltTransportClient(settings);
            //设置ip和端口
            String ips = esClientProperties.getIps();
            String[] arr = ips.split(",");
            for (String item : arr) {
                String[] ipAndPort = item.split(":");
                String ip = ipAndPort[0];
                Integer port = Integer.parseInt(ipAndPort[1]);
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), port));
            }
            if(client!=null){
                log.info("-------->es链接成功<---------");
            }
            return client;
        }catch (Exception e){
            log.error("===>es初始化失败");
            e.printStackTrace();
        }
        return null;
    }
}
