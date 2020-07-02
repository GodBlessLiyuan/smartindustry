package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.ReceiptBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 14:57
 * @description: 质量管理 分页VO
 * @version: 1.0
 */
@Data
public class QualityPageVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 收料表体ID
     */
    private Long rbid;
    /**
     * 收料单号
     */
    private String rno;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 收料类型
     * 1：PO单收料
     * 2：样品采购
     * 3：生产退料
     */
    private Byte otype;
    /**
     * 收料数量
     */
    private Integer anum;
    /**
     * 良数
     */
    private Integer gnum;
    /**
     * 不良数
     */
    private Integer bnum;
    /**
     * 质检状态
     */
    private Byte status;
    /**
     * 收料日期
     */
    private Date adate;


    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<QualityPageVO> convert(List<ReceiptBO> bos) {
        List<QualityPageVO> vos = new ArrayList<>(bos.size());
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
    public static QualityPageVO convert(ReceiptBO bo) {
        QualityPageVO vo = new QualityPageVO();
        vo.setRbid(bo.getReceiptBodyId());
        vo.setRno(bo.getReceiptNo());
        vo.setMno(bo.getMaterialNo());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setOtype(bo.getOrderType());
        vo.setAnum(bo.getAcceptNum());
        vo.setGnum(bo.getGoodNum());
        vo.setBnum(bo.getBadNum());
        vo.setStatus(bo.getQaStatus());
        vo.setAdate(bo.getAcceptDate());
        return vo;
    }
}
