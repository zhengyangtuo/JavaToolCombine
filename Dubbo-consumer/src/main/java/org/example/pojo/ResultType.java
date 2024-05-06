package org.example.pojo;

public class ResultType {
    private int code;
    private String msg;
    private Object data;

    public ResultType(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultType(int code) {
        this.code = code;
    }

    public static ResultType fail(int code, Object data) {
        return new ResultType(code,"fail",data);
    }
    public static ResultType success(int code, Object data) {
        return new ResultType(code,"success",data);
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
