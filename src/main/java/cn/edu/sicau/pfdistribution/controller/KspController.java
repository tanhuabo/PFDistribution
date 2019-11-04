package cn.edu.sicau.pfdistribution.controller;

import cn.edu.sicau.pfdistribution.entity.KspSearchResult;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;
import cn.edu.sicau.pfdistribution.Utils.ResultMsg;
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


    @PostMapping("/tripPlan.do")
    public Object findKsp(SWJTU_DTO swjtu_dto){
        List<KspSearchResult> KspResult=kspService.findKsp(swjtu_dto);
        ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(),KspResult);
        return resultMsg;
    }




    //自定义查询拥挤度
//    @Autowired
    @PostMapping("/querySectionsCrowd.do")
    public Object  GetVlumeRatio(StartParagram startParagram) {
        List<SectionIdResultData>data=kspService.getVlumeRatio(startParagram);
         ReturnResultForm returnResultForm =new ReturnResultForm(ResultStatusCode.OK.getErrcode(),ResultStatusCode.OK.getErrmsg(),data);
        return returnResultForm;
    }
}
