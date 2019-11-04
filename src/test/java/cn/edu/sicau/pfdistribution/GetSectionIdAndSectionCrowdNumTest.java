package cn.edu.sicau.pfdistribution;

import cn.edu.sicau.pfdistribution.entity.StationAndSectionPassengers;
import cn.edu.sicau.pfdistribution.service.Web.GetStationIdAndSectionIdImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GetSectionIdAndSectionCrowdNumTest {
    @Test
//    public Map<Integer,String> VRData()
    public void VRData() {
        StationAndSectionPassengers stationAndSectionPassengers = new StationAndSectionPassengers();
        GetStationIdAndSectionIdImpl getOracleData=new GetStationIdAndSectionIdImpl();
        Map<String,Integer>getMap=getOracleData.getVRDataNotParameter();
//        GetVMTest getVMTest=new GetVMTest();
//        Map<String,Integer>getMap=getVMTest.getVRDataNotParameter();
        Map<String, List<String>> sectionP= stationAndSectionPassengers.getSectionP();
        Map returnMap=new HashMap();
        Iterator it=getMap.values().iterator();
        while(it.hasNext()){
            Integer SectionID=(Integer) it.next();
            String VRData= sectionP.get(SectionID).get(1);
            returnMap.put(SectionID,VRData);
        }
//        return returnMap;
        System.out.println(returnMap);
    }
}
