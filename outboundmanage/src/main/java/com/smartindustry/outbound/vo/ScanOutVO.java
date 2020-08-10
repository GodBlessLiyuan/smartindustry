package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:50 2020/7/21
 * @version: 1.0.0
 * @description:
 */
@Data
public class ScanOutVO implements Serializable {
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
     * 当前PID包含的物料数量
     */
    private Integer num;

    public static List<ScanOutVO> convert(List<PrintLabelBO> pos) {
        List<ScanOutVO> vos = new ArrayList<>(pos.size());
        for (PrintLabelBO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static ScanOutVO convert(PrintLabelBO bo) {
        ScanOutVO vo = new ScanOutVO();
        vo.setPlid(bo.getPrintLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getNum());
        return vo;
    }
}
