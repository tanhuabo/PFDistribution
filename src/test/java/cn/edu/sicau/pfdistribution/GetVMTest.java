package cn.edu.sicau.pfdistribution;

import cn.edu.sicau.pfdistribution.entity.StationAndSectionPassengers;
import cn.edu.sicau.pfdistribution.service.Web.GetStationIdAndSectionIdImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GetVMTest {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    HashMap<String,Integer> map;

    @Autowired
    GetStationIdAndSectionIdImpl getStationIdAndSectionId;

//  @Test
//    public Map<String,Integer> getVRDataNotParameter()
    @Test
    public void GetVM()
    {

        Map returnMap = new HashMap();
        List rows=jdbcTemplate.queryForList("SELECT CZ1_ID,CZ2_ID,QJ_ID FROM \"SCOTT\".\"dic_section\"");
        Iterator it = rows.iterator();
        while(it.hasNext()) {
            Map userMap = (Map) it.next();
            Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
            Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
            String CZ_ID12=CZ1_ID.toString()+" "+CZ2_ID.toString();
            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
            returnMap.put(CZ_ID12,SectionID);
        }
        System.out.println(returnMap);
//        map=returnMap;
//         map.putAll(returnMap);
//        System.out.println(map);
//        return returnMap;
    }
//    Map<String,Integer> map = GetVM();
//    @Test
//    public void print(){
//       System.out.println(map);
//  }


    //测试
//    @Autowired
//    @Qualifier("oracleJdbcTemplate")
//    private JdbcTemplate jdbcTemplate;
//    Map getOracleDataImplMap = new HashMap();
//    List rows=jdbcTemplate.queryForList("SELECT CZ1_ID,CZ2_ID,QJ_ID FROM \"SCOTT\".\"dic_section\"");
//
////    @Test
////    public void print(){
////        System.out.println(rows);
////    }
//        Iterator it = rows.iterator();
//    @Test
//    public void getVRDataNotParameter(){
//        while(it.hasNext()) {
//            Map userMap = (Map) it.next();
//            Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
//            Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
//            String CZ12_ID=CZ1_ID.toString()+" "+CZ2_ID.toString();
//            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
//            getOracleDataImplMap.put(CZ12_ID,SectionID);
//        }
//        System.out.println(getOracleDataImplMap);
//    }
//    @Test
//    public void  getVRDataHaveParameterTest(){
//        Integer id=100;
//        getVRDataHaveParameter(id);
//    }
//    public void  getVRDataHaveParameter(Integer id){
//
//        Map returnMap = new HashMap();
//        while (it.hasNext()){
//            Map userMap = (Map) it.next();
//            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
//            if (SectionID.equals(id)){
//                Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
//                Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
//                String CZ12_ID=CZ1_ID.toString()+" "+CZ2_ID.toString();
//                getOracleDataImplMap.put(CZ12_ID,SectionID);
//            }
//        }
//        System.out.println(getOracleDataImplMap);
//    }
}
