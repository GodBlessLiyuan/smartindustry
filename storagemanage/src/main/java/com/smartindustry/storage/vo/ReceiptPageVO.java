package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.ReceiptBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 15:32
 * @description: 收料单分页VO
 * @version: 1.0
 */
@Data
public class ReceiptPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long receiptId;
    private String receiptNo;
    private Byte status;
    private String orderNo;
    private String materialNo;
    private Integer acceptNum;
    private Date acceptDate;
    private Integer stockNum;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<ReceiptPageVO> convert(List<ReceiptBO> bos) {
        List<ReceiptPageVO> vos = new ArrayList<>();
        for (ReceiptBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static ReceiptPageVO convert(ReceiptBO bo) {
        ReceiptPageVO vo = new ReceiptPageVO();

        vo.setReceiptId(bo.getReceiptBodyId());
        vo.setReceiptNo(bo.getReceiptNo());
        vo.setStatus(bo.getStatus());
        vo.setOrderNo(bo.getOrderNo());
        vo.setMaterialNo(bo.getMaterialNum());
        vo.setAcceptNum(bo.getAcceptNum());
        vo.setAcceptDate(bo.getAcceptDate());
        vo.setStockNum(bo.getStockNum());

        return vo;
    }
}
