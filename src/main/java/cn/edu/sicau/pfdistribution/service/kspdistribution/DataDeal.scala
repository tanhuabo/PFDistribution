package cn.edu.sicau.pfdistribution.service.kspdistribution

//import cn.edu.sicau.pfdistribution.dao.mysqlsave.RegionSaveInterface
//import cn.edu.sicau.pfdistribution.service.kafka.sender.KafkaPfAllocationMessageSender
import cn.edu.sicau.pfdistribution.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable


@Service
class DataDeal @Autowired()(val getOdList: GetOdList)extends Serializable {
  def dataTransfer(hour:Int,minute:Int):String={
    var Hour:String = ""
    var Minute:String =""
    if(hour<10)
      Hour = "0"+hour.toString
    else Hour = hour.toString

    if(minute<10)
      Minute = "0"+minute.toString
    else Minute =minute.toString
    val Date:String = Hour+":"+Minute+":"+"00"
    return Date
  }

  def tongHaoKspDataEmpty(): Unit ={
    getOdList.deleteAllKspRegion()
  }
  def tongHaoKspDataSave(path:String,passengers:Double,startStation:String,endStation:String): Unit ={
    getOdList.deleteAllKspRegion() //清空数据库表
//    getOdList.createKspRegionTable()
    getOdList.kspRegionAdd(path,passengers,startStation,endStation)
  } //od矩阵处理测试
  def sectionDataSave(data:mutable.Map[String, Double]): Unit ={
    for(key <- data.keys){
      val v = data(key)
      val passengers = v
      val section = key.split(" ")
      val section_in = section(0)
      val section_out = section(1)

      getOdList.saveOD(Constants.DATA_DATE_DAY,dataTransfer(Constants.DATA_DATE_HOUR,Constants.DATA_DATE_MIN),section_in, section_out, passengers)
    }
  } //od矩阵处理测试
  def stationInAndOut(data:List[mutable.Map[String, Double]]):Unit = {
    val stationIn = data.head
    val stationOut = data.tail.head
    var in_passengers:Double = 0
    var out_passengers:Double = 0
    for(key <- stationIn.keys){
      val station = key
      if(stationIn.contains(key)) {
        in_passengers = stationIn(key)
      }else{
        in_passengers = 0
      }
      if(stationOut.contains(key)) {
        out_passengers = stationOut(key)
      }else{
        out_passengers = 0
      }
      getOdList.saveStationPassengers_table(Constants.DATA_DATE_DAY,dataTransfer(Constants.DATA_DATE_HOUR,Constants.DATA_DATE_MIN), station, in_passengers, out_passengers)
    }
  }
  def saveTransferPassengers(data: mutable.Map[String, Double]):Unit ={
    for(key <- data.keys){
      getOdList.saveTransfer(Constants.DATA_DATE_DAY, dataTransfer(Constants.DATA_DATE_HOUR,Constants.DATA_DATE_MIN), key, data(key))
    }
  }
}
