package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.dao.oracle.OracleGetTransferStationsById;
import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.entity.KspSearchResult;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;
import cn.edu.sicau.pfdistribution.service.kspdistribution.MainDistribution;
import org.apache.spark.sql.execution.columnar.NULL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KspServiceImpl implements KspService {

    @Autowired
    private MainDistribution mainDistribution;

    @Autowired
    private OracleGetTransferStationsById oracleGetTransferStationsById;
    @Autowired
    SectionIdAndSectionCrowdNum sectionIdAndSectionCrowdNum;

    @Override
    public List<KspSearchResult> findKsp(SWJTU_DTO swjtu_dto) {

        List<KspSearchResult> kspSearchResults = new ArrayList<>();
        Map map = (Map) mainDistribution.getDistribution(swjtu_dto.getStartStation() + " " + swjtu_dto.getEndStation());
//        Map<String,Integer> map=new HashMap<>();
//        map.put("海峡路,南湖,四公里,南坪,南滨路,七星岗,两路口,牛角沱",0);
        Iterator it = map.keySet().iterator();
        for (int i = 1; it.hasNext(); i++) {
            List<String> odstations;
            String odstation = it.next().toString();
            odstations = Arrays.asList(odstation.split(","));
            List<List<String>> transferstations = oracleGetTransferStationsById.getTransferStations(odstations);
            List<String> stations = oracleGetTransferStationsById.getStationsById(odstations);
            KspSearchResult kspSearchResult = new KspSearchResult(Integer.toString(i), stations, transferstations);
            kspSearchResults.add(kspSearchResult);
        }
        return kspSearchResults;
    }


    @Override
    public List<KspQueryResult> getQueryInfo(QueryStationByNameOrID queryStationBy_nameOrID) {
        return null;
    }

    /**
     * @return 含区间id、区间拥挤度和区间拥挤度等级的对象列表
     * @author weiyongzhao
     */
    @Override
    public List<CrowdNumResult> getSectionCrowdNumBySectionId(GetSectionCrowdNumInitialParameter getSectionCrowdNumInitialParameter) {
        List<CrowdNumResult> list = new ArrayList<>();
        Map<Integer, String> SectionIdAndSectionCrowdNum;
        if (!getSectionCrowdNumInitialParameter.getSectionId().equals("")) {
            int sectionID = Integer.parseInt(getSectionCrowdNumInitialParameter.getSectionId());
            Map<Integer, String> map = sectionIdAndSectionCrowdNum.getSectionIdAndSectionCrowdNum(sectionID);
            SectionIdAndSectionCrowdNum = map;
        } else {
            Map<Integer, String> map = sectionIdAndSectionCrowdNum.getSectionIdAndSectionCrowdNum();
            SectionIdAndSectionCrowdNum = map;
        }
        for (Map.Entry<Integer, String> entry : SectionIdAndSectionCrowdNum.entrySet()) {
            CrowdNumResult crowdNumResult = new CrowdNumResult();
            Integer sectionId = entry.getKey();
            String crowdNum = entry.getValue();
            String crowdGrade;
            if (Double.parseDouble(crowdNum) < 0.5) {
                crowdGrade = "不拥挤";
            } else if (Double.parseDouble(crowdNum) > 0.5 && Double.parseDouble(crowdNum) < 0.8) {
                crowdGrade = "轻度拥挤";
            } else {
                crowdGrade = "十分拥挤";
            }
            crowdNumResult.setSectionId(sectionId);
            crowdNumResult.setSectionCrowdNum(crowdNum);
            crowdNumResult.setSectionCrowdInfo(crowdGrade);
            list.add(crowdNumResult);
        }
        return list;
    }
}
