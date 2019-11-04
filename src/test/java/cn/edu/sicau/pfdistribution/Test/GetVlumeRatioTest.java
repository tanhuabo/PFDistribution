package cn.edu.sicau.pfdistribution.Test;

import cn.edu.sicau.pfdistribution.Utils.ResultMsg;
import cn.edu.sicau.pfdistribution.Utils.ResultStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/modularSWJTU")
public class GetVlumeRatioTest {
    private GetVlumeRatio getVlumeRatio;
    @PostMapping("querySectionsCrowd")
    public Object getResult(InitialParameter initialParameter){
        List<ReturnParameter> resultData = getVlumeRatio.getVlumeRatio(initialParameter);
        ResultMsg2 resultMsg2 = new ResultMsg2(ResultStatusCode2.OK.getStatusCode(),ResultStatusCode2.OK.getMsg(),resultData);
        return resultMsg2;
    }

}
