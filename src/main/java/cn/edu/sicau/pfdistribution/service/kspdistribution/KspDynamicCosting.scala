package cn.edu.sicau.pfdistribution.service.kspdistribution

import org.springframework.stereotype.Service

import scala.collection.mutable
import java.io._

import org.springframework.beans.factory.annotation.Autowired

@Service
class KspDynamicCosting @Autowired()(val getParameter:GetParameter)extends Serializable {

  def stationOperatingCosts(beforeSite: String, currentSite: String): Double = {
    val a = getParameter.getA()
    val b = getParameter.getB() //校正系数
    val seat = getParameter.getSeat() //列车座位数
    val max_p = getParameter.getMaxPassengers() //列车最大乘客数
    val passengers = getParameter.intervalPassenger()//区间载客量
    var crowded_degree:Double = 0
    //拥挤度
    var cost:Double = 0
    if (passengers <= seat)
      crowded_degree = 0
    else if (passengers >= seat && passengers <= max_p)
      crowded_degree = a * (passengers - seat) / seat
    else
      crowded_degree = a * (passengers - seat) / seat + b * (seat - max_p) / max_p
    cost = getParameter.getTwoSiteTime(beforeSite,currentSite) * (1 + crowded_degree) + getParameter.getSiteStopTime(beforeSite) * (1 + crowded_degree)
    return cost
  }

  def transferFee(currentSite: String) : Double = {
    var cost = 0 //换乘费用
    cost= getParameter.transferTime()
    return cost
  }

  def perceivedCosts(currentSite: String):Double = {//站台感知费用
    /*
    具体计算预留
     */

    val inPlatformCost = 5*60 //进站站台感知费用
    return inPlatformCost
  }

  def cost_Count(kspArray:mutable.Map[Array[String],Double]):mutable.Map[Array[String],Double] = {
    var DynamicArray:mutable.Map[Array[String],Double] = mutable.Map()
    for (key <- kspArray.keys) {
      var count:Double = 0
      for (i <- 0 to (key.length - 2)) {
        count += stationOperatingCosts(key(i),key(i+1)) + transferFee(key(i+1)) +perceivedCosts(key(i+1))
      }
      DynamicArray += (key -> count)
    }
    return DynamicArray
  }

}