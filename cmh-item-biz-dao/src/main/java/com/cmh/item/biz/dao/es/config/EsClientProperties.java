package com.cmh.item.biz.dao.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "es")
public class EsClientProperties {
    /**
     * 集群名
     */
    protected String clusterName;

    /**
     * es集群名
     */
    protected String ips;

    /**
     * true表示使用客户端来嗅探集群状态
     */
    protected Boolean clientTransportSniff;


}
