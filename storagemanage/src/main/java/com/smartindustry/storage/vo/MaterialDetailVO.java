package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.LocationDetailBO;
import com.smartindustry.common.bo.sm.MaterialDetailBO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:08 2020/11/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class MaterialDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 物料id
     */
    private Long mid;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料编号
     */
    private String mno;

    /**
     * 立方米体积
     */
    private BigDecimal total;

    /**
     * 单位名称
     */
    private String muname;

    private List<LocationDetailVO> vos;

    @Data
    public static class LocationDetailVO{
        /**
         * 栈板rfid
         */
        private String rfid;
        /**
         * 仓库名称
         */
        private String wname;
        /**
         * 储位编号
         */
        private String lno;
        /**
         * 库位名称
         */
        private String lname;
        /**
         * 是否是备料区false还是成品区true
         */
        private Boolean flag;
        /**
         * 栈板显示 1 栈板
         */
        private String pallet;
        /**
         * 1 栈板的体积
         */
        private BigDecimal volume;

        /**
         * 单位名称
         */
        private String muname;
    }

    public static List<MaterialDetailVO> convert(List<MaterialDetailBO> bos) {
        List<MaterialDetailVO> vos = new ArrayList<>(bos.size());
        for (MaterialDetailBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static MaterialDetailVO convert(MaterialDetailBO bo) {
        MaterialDetailVO vo = new MaterialDetailVO();
        vo.setMid(bo.getMaterialId());
        vo.setMname(bo.getMaterialName());
        vo.setMno(bo.getMaterialNo());
        if(!bo.getBos().isEmpty() && bo.getPackageVolume()!=null){
            vo.setTotal(new BigDecimal(bo.getBos().size()).multiply(bo.getPackageVolume()));
        }
        vo.setMuname(bo.getMeasureUnitName());
        List<LocationDetailVO> vos = new ArrayList<>();
        for (LocationDetailBO detailBO : bo.getBos()){
            LocationDetailVO detailVO = new LocationDetailVO();
            detailVO.setRfid(detailBO.getRfid());
            detailVO.setWname(detailBO.getWarehouseName());
            detailVO.setLname(detailBO.getLocationName());
            detailVO.setLno(detailBO.getLocationNo());
            if(detailBO.getLocationNo() == null){
                detailVO.setFlag(false);
            }else{
                detailVO.setFlag(true);
            }
            detailVO.setVolume(detailBO.getPackageVolume());
            detailVO.setMuname(detailBO.getMeasureUnitName());
            vos.add(detailVO);
        }
        vo.setVos(vos);
        return vo;
    }
}
