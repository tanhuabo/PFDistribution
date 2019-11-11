package cn.edu.sicau.pfdistribution.service.Web;

import java.util.Map;

/**
* author： 魏永昭
* */
public interface GetStationIdAndSectionId {
    public Map getVRDataNotParameter();
    public Map<String,Integer> getVRDataHaveParameter(Integer id);
}
