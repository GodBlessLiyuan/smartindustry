package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.PickHeadBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:29 2020/7/22
 * @version: 1.0.0
 * @description:
 */
@Data
public class AberrantItemsVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private Long mid;
    private Long phid;

    private String pno;
    /**
     * 物料编号
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
     * 异常输出信息
     */
    private String abmsg;

    /**
     * 异常的解释说明
     */
    private String abdesc;

    public static List<AberrantItemsVO> convert(List<PickHeadBO> bos) {
        List<AberrantItemsVO> vos = new ArrayList<>(bos.size());
        for (PickHeadBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static AberrantItemsVO convert(PickHeadBO bo) {
        AberrantItemsVO vo = new AberrantItemsVO();
        vo.setPhid(bo.getPickHeadId());
        vo.setMno(bo.getMaterialNo());
        vo.setAbdesc(bo.getAberrantDesc());
        vo.setPno(bo.getPickNo());
        vo.setMname(bo.getMaterialName());
        vo.setMid(bo.getMaterialId());
        vo.setMdesc(bo.getMaterialDesc());
        if (bo.getRecommendPid()==null){
            vo.setAbmsg("扫描了其他推荐的PID:"+bo.getNoRecommendPid());
        }else{
            vo.setAbmsg("未扫描优先推荐的PID:"+bo.getRecommendPid()+";扫描了其他推荐的PID:"+bo.getNoRecommendPid());
        }
        return vo;
    }

}
