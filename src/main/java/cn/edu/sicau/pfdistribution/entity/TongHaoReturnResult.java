package cn.edu.sicau.pfdistribution.entity;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TongHaoReturnResult implements Serializable {
    private String dataTime;
    private ArrayList<TongHaoPathType> pathDistribution;

    public TongHaoReturnResult() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        this.dataTime = df.format(new Date());
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public ArrayList<TongHaoPathType> getPathDistribution() {
        return pathDistribution;
    }

    public void setPathDistribution(ArrayList<TongHaoPathType> pathDistribution) {
        this.pathDistribution = pathDistribution;
    }
}
