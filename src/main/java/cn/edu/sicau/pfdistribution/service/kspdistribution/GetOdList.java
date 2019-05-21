package cn.edu.sicau.pfdistribution.service.kspdistribution;

import cn.edu.sicau.pfdistribution.OracleLink;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GetOdList {
    @Autowired
    OracleLink odData;
    public List<String> getList(String inTime,long time) throws Exception {
        List<String> strList=odData.SelectOD(inTime,time);
        return strList;
    }
}
