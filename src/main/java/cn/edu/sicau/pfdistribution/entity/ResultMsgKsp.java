package cn.edu.sicau.pfdistribution.entity;

import cn.edu.sicau.pfdistribution.entity.NetworkResult;

import java.util.List;
/*
 *@author:周致远
 */

/**
 * @param status状态码
 * @param msg消息状态
 * @param data数据body
 * 定义返回的josn数据的格式
 */
public class ResultMsgKsp {
    private int status;
    private String msg;
    private List<NetworkResult> data;

    public ResultMsgKsp(int status, String msg, List<NetworkResult> data){
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

    public List<NetworkResult> getData() {
        return data;
    }

    public void setData(List<NetworkResult> data) {
        this.data = data;
    }
}
