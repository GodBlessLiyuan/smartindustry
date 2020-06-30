package com.smartindustry.storage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartindustry.common.mapper.ReceiptBodyMapper;
import com.smartindustry.common.pojo.ReceiptBodyPO;
import com.smartindustry.common.pojo.ReceiptHeadPO;
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
    public static List<ReceiptBodyPO> createPOs(ReceiptHeadPO headPO, List<ReceiptBodyDTO> dtos, ReceiptBodyMapper mapper) {
        List<ReceiptBodyPO> pos = new ArrayList<>();

        String head = headPO.getOrderType() == 1 ? ReceiptNoUtil.RECEIPT_BODY_PO : headPO.getOrderType() == 2 ? ReceiptNoUtil.RECEIPT_BODY_RP : ReceiptNoUtil.RECEIPT_BODY_YP;
        int curNum = ReceiptNoUtil.getReceiptBodyNum(mapper, head, new Date());
        for (ReceiptBodyDTO dto : dtos) {
            pos.add(ReceiptBodyDTO.createPO(headPO.getReceiptHeadId(), dto, head, ++curNum));
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
    private static ReceiptBodyPO createPO(Long headId, ReceiptBodyDTO dto, String head, int num) {
        ReceiptBodyPO po = new ReceiptBodyPO();
        po.setReceiptHeadId(headId);
        po.setReceiptNo(ReceiptNoUtil.genReceiptBodyNo(head, new Date(), num));
        po.setMaterialNo(dto.getMno());
        po.setMaterialName(dto.getMname());
        po.setMaterialType(dto.getMtype());
        po.setMaterialModel(dto.getMmodel());
        po.setMaterialDesc(dto.getMdesc());
        po.setOrderTotal(dto.getOtotal());
        po.setAcceptNum(dto.getAnum());
        po.setAcceptDate(null == dto.getAdate() ? new Date() : dto.getAdate());
        po.setGoodNum(dto.getGnum());
        po.setBadNum(dto.getBnum());
        po.setStockNum(dto.getSnum());
        po.setStatus((byte) 1);
        po.setDr((byte) 1);

        return po;
    }
}
