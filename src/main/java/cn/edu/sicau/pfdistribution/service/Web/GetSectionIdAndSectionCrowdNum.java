package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.entity.StationAndSectionPassengers;

import java.util.*;

public class GetSectionIdAndSectionCrowdNum {
    public Map<Integer,String> VRData(){
        StationAndSectionPassengers stationAndSectionPassengers = new StationAndSectionPassengers();
        GetStationIdAndSectionIdImpl getOracleData=new GetStationIdAndSectionIdImpl();
        Map<String,Integer>getMap=getOracleData.getVRDataNotParameter();
        Map<String, List<String>> sectionP= stationAndSectionPassengers.getSectionP();
        Map returnMap=new HashMap();
        Iterator it=getMap.values().iterator();
        while(it.hasNext()){
            Integer SectionID=(Integer) it.next();
            String VRData= sectionP.get(SectionID).get(1);
            returnMap.put(SectionID,VRData);
    }
        return returnMap;
    }
    public Map<Integer,String> VRData(Integer id){
        StationAndSectionPassengers stationAndSectionPassengers = new StationAndSectionPassengers();
        GetStationIdAndSectionIdImpl getOracleData=new GetStationIdAndSectionIdImpl();
        Map<String,Integer>getMap=getOracleData.getVRDataHaveParameter(id);
        Map<String, List<String>> sectionP= stationAndSectionPassengers.getSectionP();
        Map returnMap=new HashMap();
        Iterator it=getMap.values().iterator();
        while(it.hasNext()){
            Integer SectionID=(Integer) it.next();
            String VRData= sectionP.get(SectionID).get(1);
            returnMap.put(SectionID,VRData);
        }
        return returnMap;
    }

}
