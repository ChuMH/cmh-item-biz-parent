package com.cmh.item.biz.comm.enums;

/**
 * @author：初明昊
 * @data：2019/12/14 12:46
 * @description：状态枚举类
 */
public enum StatusEnum {
    DELIVERING(0,"消息投递中"),
    DELIVER_SUCCESS(1,"投递成功"),
    DELIVER_FAIL(2,"投递失败"),
    CONSUMED_SUCCESS(1,"已消费");

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
