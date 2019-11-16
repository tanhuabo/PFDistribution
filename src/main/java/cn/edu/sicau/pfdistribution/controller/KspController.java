package cn.edu.sicau.pfdistribution.controller;

import cn.edu.sicau.pfdistribution.Utils.ResultMsg2;
import cn.edu.sicau.pfdistribution.dao.Impl.OracleQueryStationByNameOrIdImpl;
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
    OracleQueryStationByNameOrIdImpl oracleQueryStationByNameOrId;
    @Autowired
    private RoadPlanningService roadPlanningService;
    /**
     *@author zhouzhiyuan
     */
    @PostMapping("/tripPlan.do")
    public Object intactFindKsp(SWJTU_DTO swjtu_dto){
        List<PathSearch> NetResult=roadPlanningService.kspResult(swjtu_dto);
        ResultMsgKsp resultMsgKsp=new ResultMsgKsp(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),NetResult);
        return resultMsgKsp;
    }

    @PostMapping("/queryByStation.do")
    public Object getQueryInfo(QueryStationByNameOrID queryStationBy_nameOrID){
        List<KspQueryResult> odAndTime= oracleQueryStationByNameOrId.findAll(queryStationBy_nameOrID);
        ResultMsg2 resultMsg = new ResultMsg2(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),odAndTime);
        return resultMsg;
    }

    @PostMapping("/querySectionsCrowd.do")
    public Object GetVolumeRatio(GetSectionCrowdNumInitialParameter getSectionCrowdNumInitialParameter) {
        List<CrowdNumResult>data=kspService.getSectionCrowdNumBySectionId(getSectionCrowdNumInitialParameter);
        ReturnResultForm returnResultForm =new ReturnResultForm(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),data);
        return returnResultForm;
    }
}
