package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.PickBodyBO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

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

    /**
     * 数量+计量单位
     */
    private String nummunit;

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
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getDemandNum());
        String mname = StringUtils.isEmpty(bo.getMeasureUnitName()) ?(" "+bo.getMeasureUnitName()): "";
        if (bo.getDemandNum() != null) {
            vo.setNummunit(bo.getDemandNum()+mname);
        }
        return vo;
    }
}
