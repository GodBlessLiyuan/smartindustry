package com.smartindustry.storage.dto;

import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.storage.constant.StorageConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:56 2020/10/26
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageHeadDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 采购入库单id
     */
    private Long shid;
    /**
     * 仓库id
     */
    private Long wid;
    /**
     * 付款方式
     */
    private Byte pmethod;
    /**
     * 供应商id
     */
    private Long sid;
    /**
     * 备注
     */
    private String extra;

    private List<StorageBodyDTO> body;

    /**
     * 标识位：用来标识是确认入库还是暂存， true为确认入库，false为暂存
     */
    private Boolean flag;

    @Data
    public static class StorageBodyDTO {
        /**
         * 表体id
         */
        private String sbid;
        /**
         * 物料ID
         */
        private Long mid;
        /**
         * 接收数量
         */
        private BigDecimal anum;
        /**
         * 车牌信息
         */
        private String cbrand;
        /**
         * 接受时间
         */
        private Date atime;
        /**
         * 储位id
         */
        private Long lid;
        /**
         * 供应商id
         */
        private Long sid;
        /**
         * 单价
         */
        private BigDecimal up;
        /**
         * 金额
         */
        private BigDecimal sp;
        /**
         * 不含税单价
         */
        private BigDecimal upn;
        /**
         * 不含税金额
         */
        private BigDecimal spn;
    }

    public static StorageHeadPO createPO(StorageHeadDTO dto) {
        StorageHeadPO po = new StorageHeadPO();
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        po.setSourceType(StorageConstant.TYPE_RAW_STORAGE);
        return buildPO(po, dto);
    }

    public static StorageHeadPO buildPO(StorageHeadPO po, StorageHeadDTO dto) {
        po.setWarehouseId(dto.getWid());
        po.setRemark(dto.getExtra());
        po.setPayMethod(dto.getPmethod());
        po.setSupplierId(dto.getSid());
        return po;
    }

    public static List<StorageBodyPO> convert(StorageHeadPO po, List<StorageBodyDTO> dtos) {
        List<StorageBodyPO> bodyPOs = new ArrayList<>();
        for (StorageBodyDTO dto : dtos) {
            bodyPOs.add(createPO(po, dto));
        }
        return bodyPOs;
    }


    public static StorageBodyPO createPO(StorageHeadPO headPO,StorageBodyDTO dto) {
        StorageBodyPO po = new StorageBodyPO();
        po.setStorageHeadId(headPO.getStorageHeadId());
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        return buildPO(po, dto);
    }

    public static StorageBodyPO buildPO(StorageBodyPO bodyPO, StorageBodyDTO dto) {
        bodyPO.setMaterialId(dto.getMid());
        bodyPO.setStorageNum(dto.getAnum());
        bodyPO.setAcceptTime(dto.getAtime());
        bodyPO.setUnitPrice(dto.getUp());
        bodyPO.setUnitPriceNotax(dto.getUpn());
        bodyPO.setSumPrice(dto.getSp());
        bodyPO.setSumPriceNotax(dto.getSpn());
        bodyPO.setLocationId(dto.getLid());
        return bodyPO;
    }


}
