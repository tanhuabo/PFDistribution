package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.entity.StationAndSectionPassengers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class GetSectionIdAndSectionCrowdNum {

    @Autowired
    GetStationIdAndSectionIdImpl getOracleData;
    @Autowired
    StationAndSectionPassengers stationAndSectionPassengers;

    public Map<Integer,String> VRData(){
        Map getMap=getOracleData.getVRDataNotParameter();
        Map<String, List<String>> sectionP= stationAndSectionPassengers.getSectionP();
        Map returnMap=new HashMap();
        Iterator keyset=getMap.keySet().iterator();
        Iterator entryset=getMap.entrySet().iterator();
        while (keyset.hasNext()){
            String key=(String) keyset.next();
            Integer value= (Integer) entryset.next();
            String VRData= sectionP.get(key).get(0);
            Integer id=value;
            returnMap.put(id,VRData);
        }


        return returnMap;

    }
    public Map<Integer,String> VRData(Integer id){
//        GetStationIdAndSectionIdImpl getOracleData = new GetStationIdAndSectionIdImpl();
        Map<String,Integer>getMap=getOracleData.getVRDataHaveParameter(id);
        Map<String, List<String>> sectionP= stationAndSectionPassengers.getSectionP();
        Map returnMap=new HashMap();
        Iterator it=getMap.keySet().iterator();
        while(it.hasNext()){
            String key=(String) it.next();
            String VRData= sectionP.get(key).get(0);
            returnMap.put(id,VRData);
        }
        return returnMap;
    }
}
