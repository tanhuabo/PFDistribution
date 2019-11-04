package cn.edu.sicau.pfdistribution.Test;

import java.util.List;

public class ResultMsg2 {
    private int status;
    private String msg;
    private List<ReturnParameter> data;
    public ResultMsg2(int status,String msg,List<ReturnParameter>data){
        this.status=status;
        this.msg=msg;
        this.data=data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ReturnParameter> getData() {
        return data;
    }

    public void setData(List<ReturnParameter> data) {
        this.data = data;
    }
}
