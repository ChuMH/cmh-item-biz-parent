package com.cmh.item.biz.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author：
 * @data：
 * @description：
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String objToStr(T obj) {
        if (null == obj) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("objToStr error: ", e);
            return null;
        }
    }
    public static <T> T strToObj(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || null == clazz) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("strToObj error: ", e);
            return null;
        }
    }
}
