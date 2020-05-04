package com.cmh.item.biz.service.impl.es;

import com.cmh.item.biz.dao.es.service.EsClientDao;
import com.cmh.item.biz.sdk.service.es.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private EsClientDao esClientDao;

    @Override
    public void add(Map<String, Object> map) {
        esClientDao.add(map);
    }
}
