package com.fitibo.eyouapp.bean;

import lombok.Data;

/**
 * Created by qianhao.zhou on 08/02/2017.
 */
@Data
public class ResultVo {

    public static final ResultVo SUCCESS = new ResultVo(0, "succeed");
    public static final ResultVo FAIL = new ResultVo(-1, "failed");

    public static ResultVo getResultByBoolean(boolean result) {
        return result ? SUCCESS : FAIL;
    }

    private int code;
    private String msg;
    private Object data;

    public ResultVo(int code, String msg) {
        this(code, msg, null);
    }

    public ResultVo(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
