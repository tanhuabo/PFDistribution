package cn.edu.sicau.pfdistribution.dao.oracle;


import java.util.List;
import java.util.Map;

public interface OracleGetod {
    public void deleteCare();
    public void creatGet();
    public void creatIn();
    public void creatOut();
    public void insertIn();
    public void insertOut();
    public void insertOd();
    public void deleteNot();
    public void deleteIn();
    public void deleteOut();
    public void deleteOd();
    Map<String,Integer> selectOd(String selectOD);
    //路径分配结果存取部分
    public void createKspRegionTable();
    public void kspRegionAdd(String path, double passenger,String startStation,String endStation);
    public void deleteAllKspRegion();
    //将仿真得到的OD结果从oracle拉取出来并组成需要格式
    public List<String> odFromOracleToList();
    public Map<String, Integer> odGet123(int hour,int min);//AFC配对OD测试数据获取
    public void savePassengers_table(String data_day,String data_hour,String station,double in_passengers,double out_passengers);
    public  void saveTransfer(String data_day,String data_hour,String transfer_station,double transfer_passengers);
}

