package com.hrms.common;

import java.io.Serializable;

/**
 * 业务处理统一返回结果
 */
public class ResultMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int CODE_SUCCESS = 200;
    public static final int CODE_ERROR = 500;

    private int code;
    private String msg;
    private Object data;

    public ResultMsg() {
    }

    public ResultMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultMsg(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResultMsg success() {
        return new ResultMsg(CODE_SUCCESS, "操作成功");
    }

    public static ResultMsg success(String msg) {
        return new ResultMsg(CODE_SUCCESS, msg);
    }

    public static ResultMsg success(String msg, Object data) {
        return new ResultMsg(CODE_SUCCESS, msg, data);
    }

    public static ResultMsg error(String msg) {
        return new ResultMsg(CODE_ERROR, msg);
    }

    public static ResultMsg error(int code, String msg) {
        return new ResultMsg(code, msg);
    }

    public boolean isSuccess() {
        return this.code == CODE_SUCCESS;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
