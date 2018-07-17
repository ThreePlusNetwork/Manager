package com.freeland.constant;

/**
 * Created by heiqie on 2018/7/17.
 */
public enum  Error {

    NOT_ENOUGH(600,"请确认你有足够的金币");
    private int code;
    private String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
