package cn.edu.sicau.pfdistribution.service.Web;

import org.springframework.stereotype.Service;

/**
 * author：weiyongzhao
*传入参数SectionID的类
* */

@Service
public class GetSectionCrowdNumInitialParameter {
    private int sectionId;

    public GetSectionCrowdNumInitialParameter(){
        this.sectionId=-1;
    }
    public GetSectionCrowdNumInitialParameter(int sectionId){
        this.sectionId=sectionId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
