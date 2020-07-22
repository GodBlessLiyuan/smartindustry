package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 20:51 2020/7/21
 * @version: 1.0.0
 * @description:
 */
@Data
public class PrintSplitVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long plid;

    private String pid;
    /**
     * 物流编码
     */
    private String mno;
    /**
     * 物料名称
     */
    private String mname;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 生产批号
     */
    private String pbatch;
    private Integer num;
    /**
     * 创建时间
     */
    private Date ctime;


    public static List<PrintSplitVO> convert(List<PrintLabelBO> pos) {
        List<PrintSplitVO> vos = new ArrayList<>(pos.size());
        for (PrintLabelBO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static PrintSplitVO convert(PrintLabelBO bo) {
        PrintSplitVO vo = new PrintSplitVO();
        vo.setPlid(bo.getPrintLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setPbatch(bo.getProduceBatch());
        vo.setCtime(bo.getCreateTime());
        vo.setNum(bo.getNum());
        return vo;
    }
}
