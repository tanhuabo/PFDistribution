package cn.edu.sicau.pfdistribution.dao.Impl;

import cn.edu.sicau.pfdistribution.dao.oracle.OracleGetod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class OracleImpl implements OracleGetod {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    private String pullOdFromOracleSql="select *from \"SCOTT\".\"ODFLOW_Demand\"";

    @Override
    public void deleteCare(){
        jdbcTemplate.update("delete   \n" +
                "from \"SCOTT\".\"ceshi_copy1\"\n" +
                "WHERE \"票卡种类\" not like '%票%' and \"票卡种类\" not like '%卡%'\n");
    }
    @Override
    public void creatGet(){
        jdbcTemplate.update("CREATE TABLE \"SCOTT\".\"test_od\" (   \n" +
                "  \"票卡号\" CHAR(20) ,\n" +
                "  \"进站点\" CHAR(100) ,\n" +
                "  \"进站时间\" TIMESTAMP ,\n" +
                "  \"出站点\" CHAR(100) ,\n" +
                "  \"出站时间\" TIMESTAMP \n" +
                ")");
    }
    @Override
    public void creatIn(){
        jdbcTemplate.update("CREATE TABLE \"SCOTT\".\"test_in\" (   --创建储存进站数据表\n" +
                "  \"票卡号\" CHAR(20) ,\n" +
                "  \"进站点\" CHAR(100) ,\n" +
                "  \"进站时间\" TIMESTAMP ,\n" +
                "\"交易前金额(元)\" CHAR(10)\n" +
                ")");
    }
    @Override
    public void creatOut(){
        jdbcTemplate.update("CREATE TABLE \"SCOTT\".\"test_out\" (  \n" +
                "  \"票卡号\" CHAR(20) ,\n" +
                "  \"出站点\" CHAR(100) ,\n" +
                "  \"出站时间\" TIMESTAMP ,\n" +
                "\"交易前金额(元)\" CHAR(10)\n" +
                ")");
    }
    @Override
    public void insertIn(){
        jdbcTemplate.update("insert into \"SCOTT\".\"test_in\"(\"票卡号\",\"进站点\",\"进站时间\",\"交易前金额(元)\")  --插入进站数据到进站数据表\n" +
                "select \"票卡号\",\"交易车站\",\"数据接收时间\",\"交易前余额(元)\"\n" +
                "from \"SCOTT\".\"ceshi_copy1\"\n" +
                "where \"交易事件\"='进站'");
    }
    @Override
    public void insertOut(){
        jdbcTemplate.update("insert into \"SCOTT\".\"test_out\"(\"票卡号\",\"出站点\",\"出站时间\",\"交易前金额(元)\")  --插入出站数据到出站数据表\n" +
                "select \"票卡号\",\"交易车站\",\"数据接收时间\",\"交易前余额(元)\"\n" +
                "from \"SCOTT\".\"ceshi_copy1\"\n" +
                "where \"交易事件\"='出站'");
    }
    @Override
    public void insertOd(){
        jdbcTemplate.update("insert into \"SCOTT\".\"test_od\"        --查找OD数据并插入到OD结果数据表\n" +
                "SELECT \"SCOTT\".\"test_in\".\"票卡号\",\"SCOTT\".\"test_in\".\"进站点\",\"SCOTT\".\"test_in\".\"进站时间\",\"SCOTT\".\"test_out\".\"出站点\",\"SCOTT\".\"test_out\".\"出站时间\"\n" +
                "FROM \"SCOTT\".\"test_in\",\"SCOTT\".\"test_out\"\n" +
                "WHERE \"SCOTT\".\"test_in\".\"票卡号\"=\"SCOTT\".\"test_out\".\"票卡号\" AND \"SCOTT\".\"test_in\".\"交易前金额(元)\"=\"SCOTT\".\"test_out\".\"交易前金额(元)\"\n" +
                "ORDER BY \"SCOTT\".\"test_in\".\"进站时间\" ASC");
    }
    @Override
    public void deleteNot(){
        jdbcTemplate.update("delete \n" +
                "from \"SCOTT\".\"test_od\"\n" +
                "where TO_CHAR(\"出站时间\")<=TO_CHAR(\"进站时间\") ");
    }
    @Override
    public void deleteIn(){
        jdbcTemplate.update("DROP TABLE \"SCOTT\".\"test_in\"\n");
    }
    @Override
    public void deleteOut() {
        jdbcTemplate.update("DROP TABLE \"SCOTT\".\"test_out\"\n");
    }
    @Override
    public void deleteOd(){
        jdbcTemplate.update("DROP TABLE \"SCOTT\".\"test_od\"\n");
    }
    @Override
    public Map<String, Integer> selectOd(String selectOD){
        Map map = new HashMap();
        List rows= jdbcTemplate.queryForList(selectOD);
        Iterator it = rows.iterator();
        while(it.hasNext()) {
            Map userMap = (Map) it.next();
            String odIn = (String) userMap.get("进站点");
            String odIn1=odIn.replace(" ", "");
            String odOut = (String) userMap.get("出站点");
            String odOut1=odOut.replace(" ", "");
            //int odPeo = (int) userMap.get("人数");
            int odPeo = Integer.parseInt(userMap.get("人数").toString());
            map.put(odIn1+" "+odOut1,odPeo);
            //strList.add(odIn1+" "+odOut1+" "+odPeo);
        }
        return map;
    }

    @Override
    public void createKspRegionTable() {
        jdbcTemplate.update("CREATE TABLE \"SCOTT\".\"kspregion\" (   \n" +
                "  \"route\" VARCHAR(500) ,\n" +
                "  \"passenger\" INT \n" +
                ")");
    }

    @Override
    public void kspRegionAdd(String path, double passenger,String startStation,String endStation) {
        jdbcTemplate.update("insert into \"SCOTT\".\"kspregion\"values(?,?,?,?)",path,passenger,startStation,endStation);
    }

    @Override
    public void deleteAllKspRegion() {
        jdbcTemplate.update("delete from \"SCOTT\".\"kspregion\" where 1=1");
    }

    @Override
    public List<String> odFromOracleToList() {
        List<String> strings=new ArrayList<>();
        List rows= jdbcTemplate.queryForList(pullOdFromOracleSql);
        Iterator it = rows.iterator();
        while(it.hasNext()) {
            Map odMap = (Map) it.next();
            String startstation = (String) odMap.get("STARTSTATION");
            String odstartstation=startstation.replace(" ", "");
            String endstation = (String) odMap.get("ENDSTATION");
            String odendstation=endstation.replace(" ", "");
            String peoplenum = (String) odMap.get("PEOPLENUM");
            String odresult=odstartstation+" "+odendstation+" "+peoplenum;
            strings.add(odresult);
        }
        return strings;
    }
    @Override
    public Map<String, Integer> odGet123(int hour,int min){//参数传日期和小时，日期格式2018-09-01到2018-09-07，小时从0到23
        Map map = new HashMap();
        String sql1=sqlChang(hour,min);
        List rows= jdbcTemplate.queryForList(sql1);
        Iterator it = rows.iterator();
        while(it.hasNext()) {
            Map userMap = (Map) it.next();
            String odIn=(String) userMap.get("in_station");
            String odOut=(String) userMap.get("out_station");
            //int odPeo = (int) userMap.get("人数");
            int odPeo = Integer.parseInt(userMap.get("passengers").toString());
            map.put(odIn+" "+odOut,odPeo);
        }
        return map;
    }
    public String sqlChang(int time,int min){//附件函数，改sql用，不用管
        String sqls="SELECT * FROM \"SCOTT\".\"od_03_09\" WHERE \"time\"='"+String.valueOf(time)+"' and \"min\"='"+String.valueOf(min)+"'";
        return sqls;
    }
//记得在接口处声明函数
    //储存站点得进出站总人数
    @Override
    public void savePassengers_table(String data_day,String data_hour,String station,double in_passengers,double out_passengers){
        jdbcTemplate.update("insert into \"SCOTT\".\"station_passengers_table\" values (?,?,?,?,?)",data_day,data_hour,station,in_passengers,out_passengers);
    }
    //储存换乘站点和换乘人数
    @Override
    public  void saveTransfer(String data_day,String data_hour,String transfer_station,double transfer_passengers){
        jdbcTemplate.update("insert into \"SCOTT\".\"transferTable\" values(?,?,?,?)",data_day,data_hour,transfer_station,transfer_passengers);
    }



}
