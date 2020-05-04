package com.cmh.item.biz.sdk.dto.log;

import com.cmh.item.biz.comm.enums.StatusEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MsgLog {

    private String msgId;
    private String msg;
    private String exchange;
    private String routingKey;
    private Integer status;
    private Integer tryCount;
    private Date nextTryTime;
    private Date createTime;
    private Date updateTime;

    public MsgLog(String msgId, String msg, String exchange, String routingKey) {
        this.msgId = msgId;
        this.msg = msg;
        this.exchange = exchange;
        this.routingKey = routingKey;

        this.status = StatusEnum.DELIVERING.getCode();
        this.tryCount = 0;

        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
        this.nextTryTime = null;
    }
}
