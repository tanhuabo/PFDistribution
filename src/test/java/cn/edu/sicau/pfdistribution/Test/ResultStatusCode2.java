package cn.edu.sicau.pfdistribution.Test;

public enum  ResultStatusCode2 {
    OK(200, "success"),
    SYSTEM_ERR(30001, "System error");

    private int statusCode;
    private String msg;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private ResultStatusCode2(int statusCode, String msg)
    {
        this.statusCode = statusCode;
        this.msg = msg;
    }

}
