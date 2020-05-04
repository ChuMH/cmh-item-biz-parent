package com.cmh.item.biz.comm.enums;

/**
 * @author：
 * @data：
 * @description：
 */
public enum MailEnum {
    TITLE_REGISTER_SUCCESS("【注册成功】注册成功邮件提醒"),
    TITLE_UPDATE_SUCCESS("【修改成功】修改成功邮件提醒"),

    CONTENT_REGISTER_SUCCESS("您在初明昊的系统注册成功，快去登录吧。 -祝您生活快乐！"),
    CONTENT_UPDATE_SUCCESS("您在初明昊的系统修改成功，快去查看吧。 -祝您生活快乐！");


    private String message;

    MailEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
