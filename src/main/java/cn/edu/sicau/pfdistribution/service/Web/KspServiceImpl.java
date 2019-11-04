package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.dao.oracle.OracleGetTransferStationsById;
import cn.edu.sicau.pfdistribution.entity.KspSearchResult;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;
import cn.edu.sicau.pfdistribution.service.kspdistribution.MainDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KspServiceImpl implements KspService {

    @Autowired
    private MainDistribution mainDistribution;

    @Autowired
    private OracleGetTransferStationsById oracleGetTransferStationsById;



    @Override
    public List<KspSearchResult> findKsp(SWJTU_DTO swjtu_dto) {

        List<KspSearchResult> kspSearchResults = new ArrayList<>();
        Map map = (Map) mainDistribution.getDistribution(swjtu_dto.getStartStation()+" "+swjtu_dto.getEndStation());
//        Map<String,Integer> map=new HashMap<>();
//        map.put("海峡路,南湖,四公里,南坪,南滨路,七星岗,两路口,牛角沱",0);
        Iterator it=map.keySet().iterator();
        for(int i=1;it.hasNext();i++){
            List<String> odstations;
            String odstation=it.next().toString();
            odstations=Arrays.asList(odstation.split(","));
            List<List<String>> transferstations=oracleGetTransferStationsById.getTransferStations(odstations);
            List<String> stations=oracleGetTransferStationsById.getStationsById(odstations);
            KspSearchResult kspSearchResult = new KspSearchResult(Integer.toString(i),stations,transferstations);
            kspSearchResults.add(kspSearchResult);
        }
        return kspSearchResults;
    }
    @Override
    public List<SectionIdResultData> getVlumeRatio(StartParagram startParagram) {
        List<SectionIdResultData>list=new ArrayList<>();
        GetSectionIdAndSectionCrowdNum getSectionIdAndSectionCrowdNum =new GetSectionIdAndSectionCrowdNum();
        int sectionID = startParagram.getSectionId();
        Map<Integer,String> VRDataMap;
        if (startParagram.getSectionId()!=0){
            Map<Integer,String> map= getSectionIdAndSectionCrowdNum.VRData(sectionID);
            VRDataMap=map;
        }else {
            Map<Integer,String> map= getSectionIdAndSectionCrowdNum.VRData();
            VRDataMap=map;
        }
        for(Map.Entry<Integer, String> entry : VRDataMap.entrySet()){
            SectionIdResultData sectionIdResultData =new SectionIdResultData();
            sectionIdResultData.setSectionId(entry.getKey());
            sectionIdResultData.setSectionCrowdNum(entry.getValue());
            list.add(sectionIdResultData);
        }
        return list;
    }

}
