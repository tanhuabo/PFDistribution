package cn.edu.sicau.pfdistribution.service.kspdistribution;

import cn.edu.sicau.pfdistribution.MysqlGetID;
import cn.edu.sicau.pfdistribution.OracleLink;
import cn.edu.sicau.pfdistribution.dao.Impl.Mysqlsavelmpl;
import cn.edu.sicau.pfdistribution.dao.Impl.OracleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
public class GetOdList implements Serializable {

    transient
    @Autowired
    OracleLink odData;
    transient
    @Autowired
    MysqlGetID getOD;
    transient
    @Autowired
    Mysqlsavelmpl saveLmpl;

    transient
    @Autowired
    OracleImpl test;


    public Map<String, Integer> odGet(int hour,int minute){
        return odData.odGet(hour,minute);
    }


    //存储AFC区间分配结果
    public void saveOD(String date_day,String date_hour,String section_in,String section_out, double passengers){
        float p = (float) passengers;
        if(Double.isNaN(passengers)){
            p = 0;
        }
        try {
            saveLmpl.odRegion(date_day,date_hour,section_in,section_out,p);
        }catch (Exception e) {
            System.out.println(p);
        }
    }

    public Map<String,Integer> getOdMap(String inTime, long time) throws Exception {
        Map<String, Integer> strMap=odData.SelectOD(inTime,time);
        return strMap;
    }
    public Map<String,Integer> test_od(String day,String hour){
        return getOD.test_CQ_od(day,hour);
    }
    //OD矩阵路径分配结果操作
    public void createKspRegionTable()
    {
        odData.createKspRegionTable();
    }
    public void kspRegionAdd(String path, double passenger,String startStation,String endStation){
        float p = (float) passenger;
        if(Double.isNaN(passenger)){
            p = 0;
        }
        try {
            odData.kspRegionAdd(path, p, startStation, endStation);
        }catch (Exception e){
            System.out.println(p);
        }
    }
    public void deleteAllKspRegion(){
        odData.deleteAllKspRegion();
    }
    //将仿真得到的OD结果从oracle拉取出来并组成需要格式
    public List<String> odFromOracleToList(){
        return odData.odFromOracleToList();
    }
    //进出站数据
    public void saveStationPassengers_table(String data_day,String data_hour,String station,double in_passengers,double out_passengers){
        float in = (float) in_passengers;
        if(Double.isNaN(in_passengers)){
            in = 0;
        }
        float out = (float) out_passengers;
        if(Double.isNaN(out_passengers)){
            out = 0;
        }
        try {
            odData.savePassengers_table(data_day,data_hour,station,in,out);
        }catch (Exception e){
            System.out.println(station + "--" + in + "--"+ out);
        }

    }

    //换乘数据
    public  void saveTransfer(String data_day,String data_hour,String transfer_station,double transfer_passengers){
        float passenger = (float) transfer_passengers;
        if(Double.isNaN(transfer_passengers)){
            passenger = 0;
        }
        try {
            odData.saveTransfer(data_day,data_hour,transfer_station,passenger);
        }catch (Exception e){
            System.out.println(transfer_station + "--" + passenger);
        }

    }
}
