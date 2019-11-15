package cn.edu.sicau.pfdistribution.service.Web;

import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.entity.KspSearchResult;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;

import java.util.List;

public interface KspService {
    public List<KspSearchResult> findKsp(SWJTU_DTO swjtu_dto);
    public List<KspQueryResult> getQueryInfo(QueryStationByNameOrID queryStationBy_nameOrID);
    public List<CrowdNumResult> getSectionCrowdNumBySectionId(GetSectionCrowdNumInitialParameter getSectionCrowdNumInitialParameter);
}
