package cn.edu.sicau.pfdistribution.dao.oracle;

import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.service.Web.QueryStationByNameOrID;

import java.util.List;

public interface OracleQueryStationByNameOrId {
    List<KspQueryResult> findAll(QueryStationByNameOrID queryStationBy_nameOrID);
}
