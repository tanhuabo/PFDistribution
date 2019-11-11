package cn.edu.sicau.pfdistribution.service.Web;
/*
 *@author:周致远
 */
import cn.edu.sicau.pfdistribution.entity.NetworkResult;
import cn.edu.sicau.pfdistribution.entity.SWJTU_DTO;

import java.util.List;

public interface RoadPlanningService {
    public List<NetworkResult> kspresult(SWJTU_DTO swjtu_dto);

}
