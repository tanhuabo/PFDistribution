package cn.edu.sicau.pfdistribution.dao.Impl;

import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.entity.QueryStationBy_NameOrID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OracleQueryStationBy_NameOrId {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    /**
     * @author 李勇平
     * 数据库查询
     * 输入车站名CZ_Name
     * 表Dic_station中查询LJM线路
     *通过LJM线路查起点站与终点站与起终点时间
     */
    String getLineODName="select LINE_START_CZM,LINE_END_CZM,LINE_NAME from \"SCOTT\".\"1-yxtLine\" where LINE_NAME= ";
    String getLineNameAndStationID="select LJM,CZ_ID from\"SCOTT\".\"dic_station\" where CZ_NAME=";
    String getLineTime="select QSZM,ZZZM,QSSJ,ZZSJ,QSQJH,ZZQJH from \"SCOTT\".\"base_lckxfa\" where QSZM=";

    public List<KspQueryResult> findAll(QueryStationBy_NameOrID queryStationBy_nameOrID){

        List<Map<String, Object>> Line_ID_map = jdbcTemplate.queryForList(getLineNameAndStationID+"'"+queryStationBy_nameOrID.getStationName()+"'");
        Iterator<Map<String, Object>> it1 = Line_ID_map.iterator();
        List<String> linesNames = new ArrayList<>();
        while (it1.hasNext()){
            Map LineName = it1.next();
            Object ljm = LineName.get("LJM");
            linesNames.add(ljm.toString());
        }
        List<Map<String, Object>> lineODNames = new ArrayList<>();
        for(String line:linesNames){
            Map map2 = jdbcTemplate.queryForMap(getLineODName+"'"+line+"'");
            lineODNames.add(map2);
        }
        Map<String,String> lineOD = new HashMap();
        Iterator<Map<String, Object>> it2 = lineODNames.iterator();
        while (it2.hasNext()){
            Map name = it2.next();
            String lineName = (String)name.get("LINE_NAME");
            String startId = (String)name.get("LINE_START_CZM");
            String endId = (String) name.get("LINE_END_CZM");
            lineOD.put(lineName,startId+" "+endId);
        }
        List<Map<String, Object>> lineTimes = new ArrayList<>();
        for(String od:lineOD.values()) {
            String startId =od.split(" ")[0];
            String endId = od.split(" ")[1];
            Map map3 = jdbcTemplate.queryForMap(getLineTime +"'"+startId+"'"+"and ZZZM="+"'"+endId+"'"+"AND ROWNUM < 2");
            lineTimes.add(map3);
        }
        Map<String,String> StartAndTime=new HashMap();
        Iterator<Map<String, Object>> it3 = lineTimes.iterator();
        while (it3.hasNext()) {
            Map name = it3.next();
            String startId = (String) name.get("QSZM");
            String endId = (String) name.get("ZZZM");
            String startTime = (String) name.get("QSSJ");
            String endTime = (String) name.get("ZZSJ");
            String startSection = name.get("QSQJH").toString();
            String endSection =name.get("ZZQJH").toString();
            StartAndTime.put(startId + " " + endId+" "+startSection, startTime + " " + endTime+" "+endSection);
        }
        List<KspQueryResult> result = new ArrayList<>();
            for(String od:StartAndTime.keySet()){
            String startId = od.split(" ")[0];
            String endId = od.split(" ")[1];
            String startSection = od.split(" ")[2];
            String time = StartAndTime.get(od);
            String startTime = time.split(" ")[0];
            String endTime=time.split(" ")[1];
            String endSection = time.split(" ")[2];
            KspQueryResult kspQueryResult1 = new KspQueryResult(Integer.parseInt(startSection),"往"+startId+"方向",startTime,endTime);
            KspQueryResult kspQueryResult2 = new KspQueryResult(Integer.parseInt(endSection),"往"+endId+"方向",startTime,endTime);
            result.add(kspQueryResult1);
            result.add(kspQueryResult2);
        }
        return result;
    }
    }

