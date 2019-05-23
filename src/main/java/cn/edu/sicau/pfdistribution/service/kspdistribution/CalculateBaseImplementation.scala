package cn.edu.sicau.pfdistribution.service.kspdistribution

import java.io._

import cn.edu.sicau.pfdistribution.service.kspcalculation.{KSPUtil, ReadExcel}
import cn.edu.sicau.pfdistribution.service.road.KServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable
import scala.collection.JavaConverters._
import scala.collection.mutable.Map

@Service
class CalculateBaseImplementation @Autowired() (val dynamicCosting:KspDynamicCosting,val getParameter:GetParameter,val kServiceImpl:KServiceImpl) extends CalculateBaseInterface with Serializable {
  override def dynamicOdPathSearch(targetOd: String):mutable.Map[Array[String],Double] = {
    val aList = targetOd.split(" ")
    val sou = aList(0)
    val tar = aList(1)
    val ksp = kServiceImpl.computeStatic(sou,tar)
    val iter = ksp.iterator()
    val passenger = 1000 //OD对的总人数，暂为所有OD设置为1000
    var text:mutable.Map[Iterator[String],Double] = mutable.Map()
    var text1:mutable.Map[Array[String],Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getNodes.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return dynamicCosting.cost_Count(text1)
  }

  override def getTransferTimes(targetOd: String):Int={
    val aList = targetOd.split(" ")
    val sou = aList(0)
    val tar = aList(1)
    val readExcel = new ReadExcel()
    val graph = readExcel.buildGrapgh("data/stationLine.xls", "data/edge.xls")
    val kspUtil = new KSPUtil()
    kspUtil.setGraph(graph)
    val ksp = kspUtil.computeODPath(sou,tar,1)
    val iter = ksp.iterator()
    var text:mutable.Map[Iterator[String],Double] = mutable.Map()
    var text1:mutable.Map[Array[String],Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getNodes.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    /*
     * 需要写对换乘次数的具体判断方法
     */
    val times = 2
    return times
  }

  override def distribution(map: Map[Array[String], Double], x: Int): Map[Array[String], Double] = {
    val e = Math.E
    val Q = -1*getParameter.getDistributionCoefficient()  //分配系数
    var p = 0.0
    var fenMu = 0.0
    val probability_Passenger = new Array[Double](10)
    val costMin = map.values.min
    val kspMap = scala.collection.mutable.Map[Array[String], Double]()
    for (value <- map.values) {
      //分配概率
      fenMu = fenMu + Math.pow(e, (Q * value / costMin))
    }
    var count = 0
    for (value <- map.values) {
      p = Math.pow(e, (Q * value / costMin)) / fenMu
      val kspPassenger = x.asInstanceOf[Double] * p //计算人数
      probability_Passenger(count) = kspPassenger
      count = count + 1
    }
    val keys = map.keySet
    var count1 = 0
    for (key <- keys) {
      kspMap += (key -> probability_Passenger(count1))
      count1 = count1 + 1
    }
    return kspMap
  }
  override def odDistributionResult(targetOd: String): mutable.Map[Array[String],Double] ={
    val aList = targetOd.split(" ")
    val sou = aList(0)
    val tar = aList(1)
    val passengers = aList(2).toInt
    val ksp = kServiceImpl.computeStatic(sou,tar)
    val iter = ksp.iterator()
    var text:mutable.Map[Iterator[String],Double] = mutable.Map()
    var text1:mutable.Map[Array[String],Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getNodes.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return distribution(text1, passengers)
  }

  //动态路径分配
  override def dynamicOdDistributionResult(targetOd: String): mutable.Map[Array[String],Double] ={
    val aList = targetOd.split(" ")
    val sou = aList(0)
    val tar = aList(1)
    val passengers = aList(2).toInt
    val ksp = kServiceImpl.computeDynamic(sou,tar)
    val iter = ksp.iterator()
    var text:mutable.Map[Iterator[String],Double] = mutable.Map()
    var text1:mutable.Map[Array[String],Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getNodes.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return distribution(dynamicCosting.cost_Count(text1), passengers)
  }

  override def odRegion(map: mutable.Map[Array[String], Double]): mutable.Map[String, Double] = {
    val odMap = scala.collection.mutable.Map[String, Double]()
    for (key <- map.keys) {
      for (i <- 0 to (key.length - 2)) {
        val str = key(i) + " " + key(i + 1)
        if (odMap.contains(str)) {
          odMap += (str -> (map(key) + odMap(str)))
        }
        else {
          odMap += (str -> map(key))
        }
      }
    }
    return odMap
  }

  override def odRegionWithTime(map: mutable.Map[Array[String], Double], interval:Int):mutable.Map[String, Double] = {
    val odMap = scala.collection.mutable.Map[String, Double]()
    val intervalTime = interval * 60
    for (key <- map.keys) {
      var count = 0
      var i = 0
      //        for (i <- 0 to (key.length - 2)) {
      while (i < key.length - 2) {
        if (count <= intervalTime) {  //满足条件的区间就累加
          count += getParameter.getTwoSiteTime(key(i), key(i + 1))
          val str = key(i) + " " + key(i + 1)
          if (odMap.contains(str)) {
            odMap += (str -> (map(key) + odMap(str)))
          }
          else {
            odMap += (str -> map(key))
          }
          i += 1
        } else
          i = key.length  //退出循环的条件
      }
    }
    return odMap
  }
}
