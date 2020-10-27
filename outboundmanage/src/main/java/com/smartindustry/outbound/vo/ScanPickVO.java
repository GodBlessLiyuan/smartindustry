package com.smartindustry.outbound.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:04 2020/7/15
 * @version: 1.0.0
 * @description: 扫码拣货检索列表
 */
@Data
public class ScanPickVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 打印标签的PID
     */
    private String pid;
    /**
     * 物料编码
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
     * 数量
     */
    private Integer num;

    public static List<ScanPickVO> convert(List<PrintLabelBO> bos) {
        List<ScanPickVO> vos = new ArrayList<>(bos.size());
        for (PrintLabelBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static ScanPickVO convert(PrintLabelBO bo) {
        ScanPickVO vo = new ScanPickVO();
        vo.setMdesc(bo.getMaterialDesc());
        vo.setMname(bo.getMaterialName());
        vo.setNum(bo.getNum());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        return vo;
    }
}
