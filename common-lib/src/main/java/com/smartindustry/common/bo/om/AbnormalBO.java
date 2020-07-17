package com.smartindustry.common.bo.om;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:24 2020/7/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class AbnormalBO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 工单拣货单号
     */
    private String pickNo;

    private MaterialItems getMaInstance() {
        return new MaterialItems();
    }
    @Data
    class MaterialItems {
        private String materialNo;
        private String materialName;
        private String materialDesc;
        private List<String> packageId;
    }

    public static AbnormalBO convert(PickHeadBO bo){
        AbnormalBO abnormalBO = new AbnormalBO();
        MaterialItems materialItems = abnormalBO.getMaInstance();
        materialItems.setMaterialDesc(bo.getMaterialDesc());
        materialItems.setMaterialName(bo.getMaterialName());
        materialItems.setMaterialNo(bo.getMaterialNo());
        materialItems.setPackageId(Arrays.asList(bo.getPackageItems().split(",")));
        abnormalBO.setPickNo(bo.getPickNo());
        return abnormalBO;
    }

    /**
     * 比较两个AbnormalBO 是否在相同的工单拣货单号+物料编号下保持相等
     * @param boListOne
     * @param boListTwo
     */
    public static void compareHave(List<AbnormalBO> boListOne,List<AbnormalBO> boListTwo){

    }
}
