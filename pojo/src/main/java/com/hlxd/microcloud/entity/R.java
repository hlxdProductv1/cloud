package com.hlxd.microcloud.entity;


import java.io.Serializable;


public class R<T> implements Serializable {

    public static final int NO_LOGIN = -1;
    public static final int SUCCESS = 0;
    public static final int FAIL = 1;
    public static final int NO_PERMISSION = 2;
    /**
     * 必选参数为空
     */
    public static final int NULL_PARAMETER = 4;
    /**
     * 系统内部异常
     */
    public static final int SYSTEM_EXCEPTION = 500;
    private static final long serialVersionUID = 1L;

    private String msg = "success";
    private int code = SUCCESS;
    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
