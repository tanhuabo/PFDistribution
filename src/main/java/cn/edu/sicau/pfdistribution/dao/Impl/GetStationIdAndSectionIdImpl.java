package cn.edu.sicau.pfdistribution.dao.Impl;


import cn.edu.sicau.pfdistribution.dao.oracle.GetStationIdAndSectionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

@Repository
public class GetStationIdAndSectionIdImpl implements GetStationIdAndSectionId {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @Override
    public Map getVRDataNotParameter(){
        Map getOracleDataImplMap = new HashMap();
        List rows=jdbcTemplate.queryForList("SELECT CZ1_ID,CZ2_ID,QJ_ID FROM \"SCOTT\".\"dic_section\"");
        Iterator it = rows.iterator();
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
    @Override
    public Map<String,Integer> getVRDataHaveParameter(Integer id){
        List rows=jdbcTemplate.queryForList("SELECT CZ1_ID,CZ2_ID,QJ_ID FROM \"SCOTT\".\"dic_section\" where QJ_ID ="+id+"");
        Iterator it = rows.iterator();
        Map returnMap = new HashMap();
        while (it.hasNext()){
            Map userMap = (Map) it.next();
            Integer SectionID = Integer.parseInt(userMap.get("QJ_ID").toString());
            if (SectionID.equals(id)){
                Integer CZ1_ID = Integer.parseInt(userMap.get("CZ1_ID").toString());
                Integer  CZ2_ID =Integer.parseInt(userMap.get("CZ2_ID").toString()) ;
                String CZ12_ID=CZ1_ID.toString()+" "+CZ2_ID.toString();
               returnMap.put(CZ12_ID,SectionID);
            }
    }
        return returnMap;
    }

}
