package cn.edu.sicau.pfdistribution.dao.Impl;
/**
 *@author ZhouZhiYuan
 */
import cn.edu.sicau.pfdistribution.dao.oracle.OracleExtra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class OracleExtraImpl implements OracleExtra {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    private String getLineSql="select * from \"SCOTT\".\"dic_linestation\" where CZ_ID=";

    @Override
    public List<Integer> sectionList(List<String> odStations) {
        List<Integer> sectionList=new ArrayList<>();

        List<String> stations=new ArrayList<>();
        /**
         * 站点id所对应的站名
         * @param odStations 一条路径所经过的所有站点的id
         */
        for(int i=0;i<odStations.size();i++) {
            List cz_name = jdbcTemplate.queryForList(getLineSql + Integer.parseInt(odStations.get(i)));
            String Cz_name;
            Iterator czit = cz_name.iterator();
            Map czMap = (Map) czit.next();
            Cz_name = (String) czMap.get("CZ_NAME");
            int len = stations.size();
            /**
             * 筛出重复站点
             */
            if (len != 0 && Cz_name.equals(stations.get(len - 1)))
                continue;
            else stations.add(Cz_name);
        }
        /*
         * 查询站名所对应的区间id
         */
        for(int i=0;i<stations.size()-1;i++){
            String station1 = stations.get(i);
            String station2 = stations.get(i+1);
            List QJID=jdbcTemplate.queryForList("SELECT QJ_ID\n" +
                    "FROM \"SCOTT\".\"dic_section\"\n" +
                    "WHERE CZ1_NAME="+"'"+ station1+"'" +"AND CZ2_NAME="+"'"+ station2 +"'");
            Integer qjID;
            Iterator QJIDit = QJID.iterator();
            while (QJIDit.hasNext()){
                Map QJmap= (Map) QJIDit.next();
                qjID= Integer.parseInt(QJmap.get("QJ_ID").toString());
                sectionList.add(qjID);
            }
        }
        return sectionList;
    }
}
