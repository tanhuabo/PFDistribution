package cn.edu.sicau.pfdistribution.entity;

public class QueryStationBy_NameOrID {
    private int station;
    private String stationName;

    public QueryStationBy_NameOrID() {
        this.station = -1;
    }

    public QueryStationBy_NameOrID(int station, String stationName) {
        this.station = station;
        this.stationName = stationName;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
}
