package com.cmh.item.biz.service.util;

import java.util.UUID;

/**
 * @author：初明昊
 * @data： 2019/12/14 12:38
 * @description：生成随机数
 */
public class RandomUtil {
    public static String UUID32() {
        String str = UUID.randomUUID().toString();
        return str.replaceAll("-", "");
    }
}
