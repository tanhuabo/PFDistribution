package cn.edu.sicau.pfdistribution;


import cn.edu.sicau.pfdistribution.dao.Impl.OracleImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OracleLink {

    @Autowired
    private OracleImpl OracleImpl;

    public Map<String,Integer> SelectOD(String inTime,long time) throws Exception{
        OracleImpl.deleteCare();
        OracleImpl.deleteOd();
        OracleImpl.creatGet();
        OracleImpl.creatIn();
        OracleImpl.creatOut();
        OracleImpl.insertIn();
        OracleImpl.insertOut();
        OracleImpl.insertOd();
        OracleImpl.deleteNot();
        OracleImpl.deleteIn();
        OracleImpl.deleteOut();
        Map<String,Integer> odList=OracleImpl.selectOd(selectOD(inTime,time));
        return odList;
    }
    public String selectOD(String inTime, long time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date =sdf.parse(inTime);
        if(date.getMinutes() + time >=60){
            date.setHours(date.getHours() + 1);
            date.setMinutes((int) (date.getMinutes() + time - 60));
        }else{
            date.setMinutes((int) (date.getMinutes() + time));
        }
        String outTime=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
        String testOD="SELECT \"进站点\",\"出站点\",COUNT(*) as 人数\n" +
                "from \"SCOTT\".\"test_od\"\n" +
                "where TO_CHAR(\"进站时间\") BETWEEN TO_CHAR(TO_TIMESTAMP('"+inTime+".000000','yyyy-mm-dd hh:mi:ss.ff')) AND TO_CHAR(TO_TIMESTAMP('"+outTime+".000000','yyyy-mm-dd hh:mi:ss.ff'))\n" +
                "GROUP BY \"进站点\",\"出站点\"\n";
        return testOD;
    }
    //OD矩阵路径分配结果操作
    public void createKspRegionTable()
    {
        OracleImpl.createKspRegionTable();
    }
    public void kspRegionAdd(String path, double passenger,String startStation,String endStation){
        OracleImpl.kspRegionAdd(path,passenger,startStation,endStation);
    }
    public void deleteAllKspRegion(){
        OracleImpl.deleteAllKspRegion();
    }
    //将仿真得到的OD结果从oracle拉取出来并组成需要格式
    public List<String> odFromOracleToList(){
        return OracleImpl.odFromOracleToList();
    }



    //样例数据处理test
    public Map<String, Integer> odGet(int hour,int minute){
        return OracleImpl.odGet123(hour,minute);
    }
    public void savePassengers_table(String data_day,String data_hour,String station,double in_passengers,double out_passengers){
        OracleImpl.savePassengers_table(data_day,data_hour,station,in_passengers,out_passengers);
    }
    public  void saveTransfer(String data_day,String data_hour,String transfer_station,double transfer_passengers){
        OracleImpl.saveTransfer(data_day,data_hour,transfer_station,transfer_passengers);
    }

}
