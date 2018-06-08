package com.fitibo.eyouapp.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageBoard {
    private int adminId;
    private String adminName;
    private String createTime;
    private String content;
    private String tag;
}
