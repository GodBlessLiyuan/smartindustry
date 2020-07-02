package com.smartindustry.storage.dto;

import com.smartindustry.common.bo.ReceiptBodyBO;
import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.pojo.ReceiptHeadPO;
import com.smartindustry.storage.constant.ReceiptConstant;
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
    private String mno;
    /**
     * 物料类型
     */
    private Byte mtype;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料型号
     */
    private String mmodel;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 订单总数
     */
    private Integer ototal;
    /**
     * 接受数量
     */
    private Integer anum;
    /**
     * 接受日期
     */
    private Date adate;
    /**
     * 良品数
     */
    private Integer gnum;
    /**
     * 非良品数
     */
    private Integer bnum;
    /**
     * 入库数量
     */
    private Integer snum;

    /**
     * 创建 pos
     *
     * @param headPO
     * @param dtos
     * @return
     */
    public static List<ReceiptBodyBO> createPOs(ReceiptHeadPO headPO, List<ReceiptBodyDTO> dtos, ReceiptBodyMapper mapper) {
        List<ReceiptBodyBO> bos = new ArrayList<>(dtos.size());

        String head = headPO.getOrderType() == 1 ? ReceiptNoUtil.RECEIPT_BODY_PO : headPO.getOrderType() == 2 ? ReceiptNoUtil.RECEIPT_BODY_RP : ReceiptNoUtil.RECEIPT_BODY_YP;
        int curNum = ReceiptNoUtil.getReceiptBodyNum(mapper, head, new Date());
        for (ReceiptBodyDTO dto : dtos) {
            bos.add(ReceiptBodyDTO.createPO(headPO.getReceiptHeadId(), dto, head, ++curNum));
        }
        return bos;
    }

    /**
     * 创建 po
     *
     * @param headId
     * @param dto
     * @return
     */
    private static ReceiptBodyBO createPO(Long headId, ReceiptBodyDTO dto, String head, int num) {
        ReceiptBodyBO bo = new ReceiptBodyBO();
        bo.setReceiptHeadId(headId);
        bo.setReceiptNo(ReceiptNoUtil.genReceiptBodyNo(head, new Date(), num));
        bo.setMaterialNo(dto.getMno());
        bo.setMaterialName(dto.getMname());
        bo.setMaterialType(dto.getMtype());
        bo.setMaterialModel(dto.getMmodel());
        bo.setMaterialDesc(dto.getMdesc());
        bo.setOrderTotal(dto.getOtotal());
        bo.setAcceptNum(dto.getAnum());
        bo.setAcceptDate(null == dto.getAdate() ? new Date() : dto.getAdate());
        bo.setGoodNum(dto.getGnum());
        bo.setBadNum(dto.getBnum());
        bo.setStockNum(dto.getSnum());
        bo.setStatus(ReceiptConstant.RECEIPT_ENTRY_LABEL);
        bo.setCreateTime(new Date());
        bo.setDr((byte) 1);
        return bo;
    }
}
