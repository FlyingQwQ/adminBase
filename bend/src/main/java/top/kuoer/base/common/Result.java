package top.kuoer.base.common;

import lombok.ToString;

@ToString
public class Result {

    private int code;
    private String msg;
    private Object data;

    public Result(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }


}
