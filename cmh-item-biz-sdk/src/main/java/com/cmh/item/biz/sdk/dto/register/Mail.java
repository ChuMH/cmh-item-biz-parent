package com.cmh.item.biz.sdk.dto.register;

import lombok.Data;

import java.util.function.Supplier;

/**
 * @author：初明昊
 * @data：2019/12/09
 * @description：邮件配置类
 */
@Data
public class Mail implements Supplier {
    //发件人
    private String from;
    //收件人
    private String to;
    //邮件标题
    private String title;
    //邮件内容
    private String content;
    //消息id
    private String msgId;

    @Override
    public Mail get() {
        return this;
    }
}
