package cn.edu.sicau.pfdistribution.controller;

import cn.edu.sicau.pfdistribution.Utils.ResultMsg2;
import cn.edu.sicau.pfdistribution.dao.Impl.OracleQueryStationBy_NameOrIdImpl;
import cn.edu.sicau.pfdistribution.entity.*;
import cn.edu.sicau.pfdistribution.Utils.ResultStatusCode;
import cn.edu.sicau.pfdistribution.service.Web.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modularSWJTU")
public class KspController {

    @Autowired
    private KspService kspService;
    @Autowired
    OracleQueryStationBy_NameOrIdImpl oracleQueryStationBy_nameOrIdImpl;

//    @PostMapping("/tripPlan.do")
//    public Object findKsp(SWJTU_DTO swjtu_dto){
//        List<KspSearchResult> KspResult=kspService.findKsp(swjtu_dto);
//        ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),KspResult);
//        return resultMsg;
//    }
    @Autowired
    private RoadPlanningService roadPlanningService;
    /*
     *@author:周致远
     */
    @PostMapping("/tripPlan.do")
    public Object intactFindKsp(SWJTU_DTO swjtu_dto){
        List<NetworkResult> NetResult=roadPlanningService.kspresult(swjtu_dto);
        ResultMsgKsp resultMsgKsp=new ResultMsgKsp(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),NetResult);
        return resultMsgKsp;
    }
    @PostMapping("/queryByStation.do")
    public Object getQueryInfo(QueryStationBy_NameOrID queryStationBy_nameOrID){
        List<KspQueryResult> odAndTime= oracleQueryStationBy_nameOrIdImpl.findAll(queryStationBy_nameOrID);
        ResultMsg2 resultMsg = new ResultMsg2(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),odAndTime);
        return resultMsg;
    }
    @PostMapping("/querySectionsCrowd.do")
    public Object GetVolumeRatio(StartParagram startParagram) {
        List<SectionIdResultData>data=kspService.getVlumeRatio(startParagram);
        ReturnResultForm returnResultForm =new ReturnResultForm(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),data);
        return returnResultForm;
    }
}
