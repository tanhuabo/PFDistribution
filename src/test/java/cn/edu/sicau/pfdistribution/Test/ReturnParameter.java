package cn.edu.sicau.pfdistribution.Test;

public class ReturnParameter {
    private int sectionId;
    private String sectionCrowdNum;
    private String sectionCrowdInfo;

    public ReturnParameter(int sectionId, String sectionCrowdNum, String sectionCrowdInfo){
        this.sectionId=sectionId;
        this.sectionCrowdNum=sectionCrowdNum;
        this.sectionCrowdInfo=sectionCrowdInfo;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionCrowdNum() {
        return sectionCrowdNum;
    }

    public void setSectionCrowdNum(String sectionCrowdNum) {
        this.sectionCrowdNum = sectionCrowdNum;
    }

    public String getSectionCrowdInfo() {
        return sectionCrowdInfo;
    }

    public void setSectionCrowdInfo(String sectionCrowdInfo) {
        this.sectionCrowdInfo = sectionCrowdInfo;
    }
}
