package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 10:34
 * @description: 拣货单表体
 * @version: 1.0
 */
@Data
public class PickBodyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 拣货单表体ID
     */
    private Long pbid;
    /**
     * PID
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

    private Integer pnum;


    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static PickBodyVO convert(PickBodyBO bo) {
        PickBodyVO vo = new PickBodyVO();
        vo.setPbid(bo.getPickBodyId());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getDemandNum());
        vo.setPnum(bo.getPickNum());
        return vo;
    }

    public static List<PickBodyVO> convert(List<PickBodyBO> bos) {
        List<PickBodyVO> vos = new ArrayList<>(bos.size());
        for (PickBodyBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }
}
