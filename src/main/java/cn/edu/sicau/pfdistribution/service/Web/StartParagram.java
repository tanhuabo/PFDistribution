package cn.edu.sicau.pfdistribution.service.Web;
/**
传入参数SectionID的类
* */
public class StartParagram {
    private int sectionId;
    public StartParagram(int sectionId){
        this.sectionId=sectionId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
