package cn.edu.sicau.pfdistribution.service.Web;

/**
 * author：weiyongzhao
 * data列表中的类
 */

public class SectionIdResultData {
    private int sectionId;
    private String sectionCrowdNum;
    private String sectionCrowdInfo;

    public SectionIdResultData(){

    }
    public SectionIdResultData(int sectionId, String sectionCrowdNum, String sectionCrowdInfo){
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
