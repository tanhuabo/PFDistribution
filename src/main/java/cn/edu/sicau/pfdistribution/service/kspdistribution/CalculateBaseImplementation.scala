package cn.edu.sicau.pfdistribution.service.kspdistribution

import java.io._
import java.util

import cn.edu.sicau.pfdistribution.entity.{DirectedEdge, DirectedPath, LineIdAndSectionTime, Risk}
import cn.edu.sicau.pfdistribution.service.kspcalculation.Edge
import cn.edu.sicau.pfdistribution.service.netrouter.JsonTransfer
import cn.edu.sicau.pfdistribution.service.road.KServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.Map

@Service
class CalculateBaseImplementation @Autowired() (dynamicCosting:KspDynamicCosting,getParameter:GetParameter,kServiceImpl: KServiceImpl,jsonTransfer:JsonTransfer,risk: Risk,
                                                lineIdAndSectionTime:LineIdAndSectionTime) extends CalculateBaseInterface with Serializable { //,val kServiceImpl:KServiceImpl
 /* @transient
  val readExcel = new ReadExcel()
  @transient
  val graph = readExcel.buildGrapgh("data/stationLine.xls", "data/edge.xls")
  val kspUtil = new KSPUtil()
  kspUtil.setGraph(graph)*/
  //val risk:Risk = jsonTransfer.risk
  override def staticOdPathSearch(targetOd: String):mutable.Map[Array[DirectedEdge], Double] = {
   val aList = targetOd.split(" ")
   val sou = aList(0)
   val tar = aList(1)
   /*    val readExcel = new ReadExcel()
       val graph = readExcel.buildGrapgh("data/stationLine.xls", "data/edge.xls")
       val kspUtil = new KSPUtil()
       kspUtil.setGraph(graph)
       val ksp = kspUtil.computeODPath(sou,tar,2)*/
   val OD:Map[String, String] = Map(targetOd -> targetOd)
   val ksp1:util.Map[String, util.List[DirectedPath]] = kServiceImpl.computeStatic(OD.asJava, "PARAM_NAME", "RETURN_ID")
   val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
   if(ksp == null){
     println("错误OD"+targetOd)
     return null
   }
   val iter = ksp.iterator()
   var text:mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
   var text1:mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
   while(iter.hasNext) {
     val p = iter.next()
     //      一条路径的站点构成
     val nodesIter = p.getEdges.iterator()
     //      println("费用:"  + p.getTotalCost)
     text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
     //      println(text.toList)
   }
   for (key <- text.keys) {
     val myArray = key.toArray
     text1 += (myArray -> text.apply(key))
   }

   return text1
 }
  override def dynamicOdPathSearch(targetOd: String):mutable.Map[Array[DirectedEdge], Double] = {
    val aList = targetOd.split(" ")
    val sou = aList(0)
    val tar = aList(1)
    val OD:Map[String, String] = Map(targetOd -> targetOd)
    val ksp1:util.Map[String, util.List[DirectedPath]] = kServiceImpl.computeDynamic(OD.asJava, "PARAM_NAME", "RETURN_ID",risk)
    val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
    if (ksp == null) {
      println("错误OD" + targetOd)
      return null
    } else {
      val iter = ksp.iterator()
      var text: mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
      var text1: mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
      while (iter.hasNext) {
        val p = iter.next()
        //      一条路径的站点构成
        val nodesIter = p.getEdges.iterator()
        //      println("费用:"  + p.getTotalCost)
        text += (nodesIter.asScala -> p.getTotalCost) //静态费用
        //      println(text.toList)
      }
      for (key <- text.keys) {
        val myArray = key.toArray
        text1 += (myArray -> text.apply(key))
      }
      return dynamicCosting.cost_Count(text1)
    }
  }

    override def kspDistribution(map: Map[Array[DirectedEdge], Double], x: Int): mutable.Map[Array[DirectedEdge], Double] = {
      val e = Math.E
      val Q = -1*getParameter.getDistributionCoefficient()  //分配系数
      var p = 0.0
      var fenMu = 0.0
      val probability_Passenger = new Array[Double](1000)
      val costMin = map.values.min
      val kspMap = scala.collection.mutable.Map[Array[DirectedEdge], Double]()
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

  override def odDistributionResult(targetOd: String,allKsp:mutable.Map[String, util.List[DirectedPath]],odMap:mutable.Map[String,Integer]): mutable.Map[Array[DirectedEdge], Double] ={
    val ksp:util.List[DirectedPath] = allKsp(targetOd)
    val passengers:Int = odMap(targetOd).toInt
    val iter = ksp.iterator()
    var text:mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
    var text1:mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getEdges.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return kspDistribution(text1, passengers)
  }

  override def odDistributionResultTest(targetOd: String,odMap:mutable.Map[String,Integer]): mutable.Map[Array[DirectedEdge], Double] ={
    /*val OD = targetOd.split(" ")
    val O:String = OD(0)
    val D:String = OD(1)*/
    val OD:Map[String, String] = Map(targetOd -> targetOd)
    val ksp1:util.Map[String, util.List[DirectedPath]] = kServiceImpl.computeStatic(OD.asJava, "PARAM_NAME", "RETURN_ID")
    val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
    if(ksp == null){
      println("错误OD"+targetOd)
      return null
    }
    val passengers:Int = odMap(targetOd).toInt
      val iter = ksp.iterator()
      var text:mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
      var text1:mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
      while(iter.hasNext) {
        val p = iter.next()
        //      一条路径的站点构成
        val nodesIter = p.getEdges.iterator()
        //      println("费用:"  + p.getTotalCost)
        text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      }
      //      println(text.toList)
      for (key <- text.keys) {
        val myArray = key.toArray
        text1 += (myArray -> text.apply(key))
      }
      return kspDistribution(text1, passengers)
  }

  //动态路径分配
  override def dynamicOdDistributionResult(targetOd: String,odMap:mutable.Map[String,Integer]): mutable.Map[Array[DirectedEdge], Double] ={
    val OD:Map[String, String] = Map(targetOd -> targetOd)
    val ksp1:util.Map[String, util.List[DirectedPath]] = kServiceImpl.computeDynamic(OD.asJava, "PARAM_NAME", "RETURN_ID",risk)
    val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
    if(ksp == null){
      println("错误OD"+targetOd)
      return null
    }
    val passengers:Int = odMap(targetOd).toInt
    val iter = ksp.iterator()
    var text:mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
    var text1:mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
    while(iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getEdges.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost)   //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return kspDistribution(dynamicCosting.cost_Count(text1), passengers)
  }
  override def tongHaoStaticOdDistributionResult(targetOd: String,odMap:mutable.Map[String,String]): mutable.Map[Array[DirectedEdge], Double] ={
    val OD:Map[String, String] = Map(targetOd -> targetOd)
    var ksp1:util.Map[String, util.List[DirectedPath]] = null
    ksp1 = kServiceImpl.computeStatic(OD.asJava, "PARAM_ID", "RETURN_ID")
    val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
    if(ksp == null){
      println("错误OD"+targetOd)
      return null
    }
    val passengers: Int = odMap(targetOd).toDouble.toInt
    val iter = ksp.iterator()
    var text: mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
    var text1: mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
    while (iter.hasNext) {
      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getEdges.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost) //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    return kspDistribution(text1, passengers)
  }
  override def tongHaoDynamicOdDistributionResult(targetOd: String,odMap:mutable.Map[String,String]): mutable.Map[Array[DirectedEdge], Double] ={
    var text:mutable.Map[Iterator[DirectedEdge], Double] = mutable.Map()
    var text1:mutable.Map[Array[DirectedEdge], Double] = mutable.Map()
    val OD:Map[String, String] = Map(targetOd -> targetOd)
    val ksp1:util.Map[String, util.List[DirectedPath]] = kServiceImpl.computeDynamic(OD.asJava, "PARAM_ID", "RETURN_ID",risk)
    val ksp:util.List[DirectedPath] = ksp1.get(targetOd)
    val passengers: Int = odMap(targetOd).toDouble.toInt
    val iter = ksp.iterator()
    while (iter.hasNext) {

      val p = iter.next()
      //      一条路径的站点构成
      val nodesIter = p.getEdges.iterator()
      //      println("费用:"  + p.getTotalCost)
      text += (nodesIter.asScala -> p.getTotalCost) //静态费用
      //      println(text.toList)
    }
    for (key <- text.keys) {
      val myArray = key.toArray
      text1 += (myArray -> text.apply(key))
    }
    val v:Array[Double] = text1.values.toArray
    if(v.length != 0 && v(0).toInt == -1)
      return text1 //处理未搜索到路径的结果
    else
      return kspDistribution(dynamicCosting.cost_Count(text1), passengers)
  }

  override def odRegion(map: mutable.Map[Array[DirectedEdge], Double]): mutable.Map[String, Double] = {
    val odMap = scala.collection.mutable.Map[String, Double]()
    for (key <- map.keys) {
      for (i <- 0 to (key.length - 1)) {
        val dEdge: DirectedEdge = key(i)
        val edge: Edge = dEdge.getEdge
        val str =  edge.getFromNode + " " + edge.getToNode
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
  //站点进出站人数
  override def stationInAndOutP(map: mutable.Map[Array[DirectedEdge], Double]): List[mutable.Map[String, Double]] = {
    val stationIn= scala.collection.mutable.Map[String, Double]()
    val stationOut = scala.collection.mutable.Map[String, Double]()
    for (key <- map.keys) {
      val dEdge1: DirectedEdge = key(0)
      val eg1: Edge = dEdge1.getEdge
      val dEdge2: DirectedEdge = key(key.length - 1)
      val eg2: Edge = dEdge2.getEdge
      val inStation = eg1.getFromNode
      val outStation = eg2.getToNode
      if (stationIn.contains(inStation)) {
        stationIn += (inStation -> (map(key) + stationIn(inStation)))
      }
      else {
        stationIn += (inStation -> map(key))
      }
      if (stationOut.contains(outStation)) {
        stationOut += (outStation -> (map(key) + stationOut(outStation)))
      }
      else {
        stationOut += (outStation -> map(key))
      }
    }
    val dataList:List[Map[String, Double]] = List(stationIn,stationOut)
    return dataList
  }

  //换乘人数
  override def transferPassengers(map: mutable.Map[Array[DirectedEdge], Double]): mutable.Map[String, Double]={
    val transferPassengerMap = scala.collection.mutable.Map[String, Double]()
    val CZMap:mutable.Map[Integer, Integer]=lineIdAndSectionTime.getStationIdToLineId.asScala
    for (key <- map.keys) {
      for (i <- 0 to (key.length - 2)) {
        val dEdge1: DirectedEdge = key(i)
        val eg1: Edge = dEdge1.getEdge
        val dEdge2: DirectedEdge = key(i + 1)
        val eg2: Edge = dEdge2.getEdge
        val a = eg1.getFromNode
        val b = eg2.getToNode
        if (CZMap(a.toInt) != CZMap(b.toInt)) {
          val station = eg1.getToNode
          if(transferPassengerMap.contains(station)){
            transferPassengerMap += (station -> (map(key) + transferPassengerMap(station)))
          }else
            transferPassengerMap += (station -> map(key))
        }
      }
    }
    return transferPassengerMap
  }

  override def odRegionWithTime(map: mutable.Map[Array[DirectedEdge], Double], interval:Int):mutable.Map[String, Double] = {
    val odMap = scala.collection.mutable.Map[String, Double]()
    val intervalTime = interval * 60
    for (key <- map.keys) {
      var count = 0
      var i = 0
      //        for (i <- 0 to (key.length - 2)) {
      while (i < key.length - 1) {
        if (count <= intervalTime) {  //满足条件的区间就累加
          val dEdge: DirectedEdge = key(i)
          val edge: Edge = dEdge.getEdge
          val str =  edge.getFromNode + " " + edge.getToNode
          count += getParameter.getTwoSiteTime(edge.getFromNode, edge.getToNode) + getParameter.getSiteStopTime(edge.getToNode)
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
