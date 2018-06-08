package com.fitibo.eyouapp.constants;

/**
 * Created by yerunjie on 2018/3/1
 *
 * @author yerunjie
 */
public enum MessageBoardTag {
    NONE("无"),
    PASSWORD("密码"),
    OPERATE("操作"),
    FUNCTION("功能"),
    SKU("库存"),
    OP("内部"),
    RECENT("近期"),
    OTHER("其他")
    ;
    private final String name;

    MessageBoardTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
