package cn.edu.sicau.pfdistribution.service.Web;

import org.springframework.stereotype.Service;

/**
传入参数SectionID的类
* */

@Service
public class StartParagram {
    private int sectionId;

    public StartParagram(){
        this.sectionId=-1;
    }
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
