package com.smartindustry.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.storage.util.ReceiptNoUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 16:34
 * @description: 收料管理表体
 * @version: 1.0
 */
@Data
public class ReceiptBodyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 物料编码
     */
    @JsonProperty("mNo")
    private String mNo;
    /**
     * 物料类型
     */
    @JsonProperty("mType")
    private Byte mType;
    /**
     * 物料描述
     */
    @JsonProperty("mDesc")
    private String mDesc;
    /**
     * 订单总数
     */
    @JsonProperty("oTotal")
    private Integer oTotal;
    /**
     * 接受数量
     */
    @JsonProperty("aNum")
    private Integer aNum;
    /**
     * 接受日期
     */
    @JsonProperty("aDate")
    private Date aDate;
    /**
     * 良品数
     */
    @JsonProperty("gNum")
    private Integer gNum;
    /**
     * 非良品数
     */
    @JsonProperty("bNum")
    private Integer bNum;
    /**
     * 入库数量
     */
    @JsonProperty("sNum")
    private Integer sNum;

    /**
     * 创建 pos
     *
     * @param headId
     * @param dtos
     * @return
     */
    public static List<ReceiptBodyPO> createPOs(Long headId, List<ReceiptBodyDTO> dtos, ReceiptBodyMapper mapper) {
        List<ReceiptBodyPO> pos = new ArrayList<>();

        int curNum = ReceiptNoUtil.getReceiptNum(mapper, new Date());
        for (ReceiptBodyDTO dto : dtos) {
            pos.add(ReceiptBodyDTO.createPO(headId, dto, ++curNum));
        }
        return pos;
    }

    /**
     * 创建 po
     *
     * @param headId
     * @param dto
     * @return
     */
    private static ReceiptBodyPO createPO(Long headId, ReceiptBodyDTO dto, int num) {
        ReceiptBodyPO po = new ReceiptBodyPO();
        po.setReceiptHeadId(headId);
        po.setReceiptNo(ReceiptNoUtil.genReceiptNo(new Date(), num));
        po.setMaterialNo(dto.getMNo());
        po.setMaterialType(dto.getMType());
        po.setMaterialDesc(dto.getMDesc());
        po.setOrderTotal(dto.getOTotal());
        po.setAcceptNum(dto.getANum());
        po.setAcceptDate(null == dto.getADate() ? new Date() : dto.getADate());
        po.setGoodNum(dto.getGNum());
        po.setBadNum(dto.getBNum());
        po.setStockNum(dto.getSNum());
        po.setStatus((byte) 1);
        po.setDr((byte) 1);

        return po;
    }
}
