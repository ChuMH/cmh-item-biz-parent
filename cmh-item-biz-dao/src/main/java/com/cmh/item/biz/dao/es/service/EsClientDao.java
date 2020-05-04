package com.cmh.item.biz.dao.es.service;


import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class EsClientDao {

    @Resource
    private Client esClient;

    /**
     * 保存person
     */
    public void add(Map<String, Object> map) {
        esClient.prepareIndex("person","_doc")
                .setSource(map)
                .get();
    }
}
