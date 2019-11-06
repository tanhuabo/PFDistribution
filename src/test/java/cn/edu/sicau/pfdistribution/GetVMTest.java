package cn.edu.sicau.pfdistribution;


import cn.edu.sicau.pfdistribution.dao.oracle.GetStationIdAndSectionId;
import cn.edu.sicau.pfdistribution.service.Web.KspService;
import cn.edu.sicau.pfdistribution.service.Web.StartParagram;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GetVMTest {
    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    HashMap<String,Integer> map;

    @Autowired
    GetStationIdAndSectionId getStationIdAndSectionId;
    @Autowired
    KspService kspService;
    @Autowired
    StartParagram startParagram;
    @Test
    public void GetVM()
    {
        System.out.println(getStationIdAndSectionId.getVRDataNotParameter());
    }


    //    Map<String,Integer> map = GetVM();
/*
    @Test
    public void print(){
       System.out.println(GetVM());
  }
*/

}
