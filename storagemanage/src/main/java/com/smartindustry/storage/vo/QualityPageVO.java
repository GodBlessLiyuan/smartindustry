package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.ReceiptBO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

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
     * 检验类型
     */
    private Byte ttype;
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
     * 收料数量+计量单位
     */
    private String anummunit;
    /**
     * 良数
     */
    private Integer gnum;

    /**
     * 良数+计量单位
     */
    private String gnummunit;
    /**
     * 不良数
     */
    private Integer bnum;

    /**
     * 不良数+计量单位
     */
    private String bnummunit;
    /**
     * 质检状态
     */
    private Byte status;
    /**
     * 接受日期
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
        vo.setTtype(bo.getTestType());
        vo.setOtype(bo.getSourceType());
        vo.setAnum(bo.getAcceptNum());
        vo.setGnum(bo.getGoodNum());
        vo.setBnum(bo.getBadNum());
        vo.setStatus(bo.getQaStatus());
        vo.setAdate(bo.getAcceptDate());
        String munit = StringUtils.isEmpty(bo.getMeasureUnitName()) ?"":(" "+bo.getMeasureUnitName());
        if (bo.getAcceptNum() != null) {
            vo.setAnummunit(bo.getAcceptNum()+munit);
        }
        if (bo.getGoodNum() != null) {
            vo.setGnummunit(bo.getGoodNum()+munit);
        }
        if (bo.getBadNum() != null) {
            vo.setBnummunit(bo.getBadNum() +munit);
        }
        return vo;
    }
}
