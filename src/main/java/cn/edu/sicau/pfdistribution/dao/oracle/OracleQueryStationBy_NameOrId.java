package cn.edu.sicau.pfdistribution.dao.oracle;

import cn.edu.sicau.pfdistribution.entity.KspQueryResult;
import cn.edu.sicau.pfdistribution.entity.QueryStationBy_NameOrID;

import java.util.List;

public interface OracleQueryStationBy_NameOrId {
    List<KspQueryResult> findAll(QueryStationBy_NameOrID queryStationBy_nameOrID);
}
