package com.ftj.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 消息
 * Created by fengtj on 2022/5/15 19:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {

    private String from;

    private String to;

    private String content;

    private LocalDateTime date;

    private String fromNickName;
}
