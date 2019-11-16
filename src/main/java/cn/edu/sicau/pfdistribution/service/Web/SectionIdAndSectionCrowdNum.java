package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.entity.StationAndSectionPassengers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author weiyongzhao
 * retunr 返回区间id和区间拥挤度的map集合
 **/
@Service
public class SectionIdAndSectionCrowdNum {

    @Autowired
    GetCZ12IdFromDatabaseImpl getOracleData;
    @Autowired
    StationAndSectionPassengers stationAndSectionPassengers;

    public Map<Integer, String> getSectionIdAndSectionCrowdNum() {
        Map getMap = getOracleData.getCZ12IDNoParameterFromDatabase();
        Map<String, List<String>> sectionP = stationAndSectionPassengers.getSectionP();
        Map result = new HashMap();
        Iterator keySet = getMap.keySet().iterator();
        Iterator entrySet = getMap.entrySet().iterator();
        while (keySet.hasNext()) {
            String key = (String) keySet.next();
            Integer value = (Integer) entrySet.next();
            String sectionCrowdNum = sectionP.get(key).get(0);
            Integer id = value;
            result.put(id, sectionCrowdNum);
        }
        return result;
    }

    public Map<Integer, String> getSectionIdAndSectionCrowdNum(Integer id) {
        Map<String, Integer> getMap = getOracleData.getCZ12IDWithParameterFromDatabase(id);
        Map<String, List<String>> sectionP = stationAndSectionPassengers.getSectionP();
        Map result = new HashMap();
        Iterator it = getMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String sectionCrowdNum = sectionP.get(key).get(0);
            result.put(id, sectionCrowdNum);
        }
        return result;
    }
}
