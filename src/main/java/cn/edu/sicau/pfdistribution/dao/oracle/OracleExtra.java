package cn.edu.sicau.pfdistribution.dao.oracle;

/*
 *@author:周致远
 */



import java.util.List;

public interface OracleExtra {
    /**
     * @param odStations
     * @return sectionList
     */
    List<Integer> sectionList(List<String> odStations);

}
