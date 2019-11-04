package cn.edu.sicau.pfdistribution.service.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;


public class GetStationIdAndSectionIdImpl implements GetStationIdAndSectionId {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    //StationAndSectionPassengers stationAndSectionPassengers = new StationAndSectionPassengers();
    Map getOracleDataImplMap = new HashMap();

        List rows=jdbcTemplate.queryForList("SELECT CZ1_ID,CZ2_ID,QJ_ID FROM \"SCOTT\".\"dic_section\"");
        Iterator it = rows.iterator();

    public Map<String,Integer> getVRDataNotParameter(){
        while(it.hasNext()) {
            Map userMap = (Map) it.next();
            Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
            Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
            String CZ12_ID=CZ1_ID.toString()+" "+CZ2_ID.toString();
            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
            getOracleDataImplMap.put(CZ12_ID,SectionID);
        }
        return getOracleDataImplMap;
    }

    public Map<String,Integer> getVRDataHaveParameter(Integer id){

        Map returnMap = new HashMap();
        while (it.hasNext()){
            Map userMap = (Map) it.next();
            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
            if (SectionID.equals(id)){
                Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
                Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
                String CZ12_ID=CZ1_ID.toString()+" "+CZ2_ID.toString();
                getOracleDataImplMap.put(CZ12_ID,SectionID);
            }
        }
     return returnMap;
    }
   }








