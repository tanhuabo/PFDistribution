package cn.edu.sicau.pfdistribution.controller;

import cn.edu.sicau.pfdistribution.Utils.ResultMsg2;
import cn.edu.sicau.pfdistribution.dao.Impl.OracleQueryStationBy_NameOrId;
import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.entity.KspSearchResult;
import cn.edu.sicau.pfdistribution.entity.QueryStationBy_NameOrID;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;
import cn.edu.sicau.pfdistribution.Utils.ResultMsg;
import cn.edu.sicau.pfdistribution.Utils.ResultStatusCode;
import cn.edu.sicau.pfdistribution.service.Web.KspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/modularSWJTU")
public class KspController {

    @Autowired
    private KspService kspService;
    @Autowired
    OracleQueryStationBy_NameOrId oracleQueryStationBy_nameOrId;

    @PostMapping("/tripPlan.do")
    public Object findKsp(SWJTU_DTO swjtu_dto){
        List<KspSearchResult> KspResult=kspService.findKsp(swjtu_dto);
        ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),KspResult);
        return resultMsg;
    }
    @PostMapping("/queryByStation.do")
    public Object getQueryInfo(QueryStationBy_NameOrID queryStationBy_nameOrID){
        List<KspQueryResult> odAndTime=oracleQueryStationBy_nameOrId.findAll(queryStationBy_nameOrID);
        ResultMsg2 resultMsg = new ResultMsg2(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),odAndTime);
        return resultMsg;
    }
}
