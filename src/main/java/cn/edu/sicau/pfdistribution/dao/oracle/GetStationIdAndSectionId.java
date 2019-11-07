package cn.edu.sicau.pfdistribution.dao.oracle;

import java.util.Map;

public interface GetStationIdAndSectionId {
    public Map getVRDataNotParameter();
    public Map<String,Integer> getVRDataHaveParameter(Integer id);
}
