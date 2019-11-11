package cn.edu.sicau.pfdistribution.service.Web;

import java.util.List;

/**
 * author:weiyongzhao
 * json格式下status，msg，data返回类
 **/
public class ReturnResultForm {
    private int status;
    private String msg;
    private List<SectionIdResultData>data;

    public ReturnResultForm(int status, String msg, List<SectionIdResultData>data){
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

    public List<SectionIdResultData> getData() {
        return data;
    }

    public void setData(List<SectionIdResultData> data) {
        this.data = data;
    }
}
