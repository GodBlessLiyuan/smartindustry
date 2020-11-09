package com.smartindustry.pda.vo;

import com.smartindustry.common.bo.om.OutboundBodyBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageBodyBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.pda.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/10/31 11:34
 * @description: 列表区数据
 * @version: 1.0
 */
@Data
public class PdaListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 出/入库单ID
     */
    private Long hid;
    /**
     * 单号
     */
    private String sno;
    /**
     * 出库物料
     */
    private List<String> minfos;
    /**
     * 状态：1-入库；2-出库
     */
    private Byte status;


    /**
     * 设置出库列表数据
     *
     * @param firstStorage 是否优先入库
     * @param shBOs        入库数据
     * @param ohBOs        出库数据
     * @return
     */
    public static List<PdaListVO> convert(Boolean firstStorage, List<StorageHeadBO> shBOs, List<OutboundHeadBO> ohBOs) {
        List<PdaListVO> vos = new ArrayList<>(shBOs.size() + ohBOs.size());
        if (firstStorage) {
            for (StorageHeadBO bo : shBOs) {
                vos.add(convert(bo));
            }
            for (OutboundHeadBO bo : ohBOs) {
                vos.add(convert(bo));
            }
        } else {
            for (OutboundHeadBO bo : ohBOs) {
                vos.add(convert(bo));
            }
            for (StorageHeadBO bo : shBOs) {
                vos.add(convert(bo));
            }
        }
        return vos;
    }

    private static PdaListVO convert(OutboundHeadBO bo) {
        PdaListVO vo = new PdaListVO();
        vo.setHid(bo.getOutboundHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setStatus(CommonConstant.FLAG_OUTBOUND);

        if (null != bo.getBodyBOs() && bo.getBodyBOs().size() > 0) {
            List<String> minfo = new ArrayList<>(bo.getBodyBOs().size());
            for (OutboundBodyBO bodyBO : bo.getBodyBOs()) {
                minfo.add(bodyBO.getMaterialName() + " " + bodyBO.getMaterialModel());
            }
            vo.setMinfos(minfo);
        }

        return vo;
    }

    private static PdaListVO convert(StorageHeadBO bo) {
        PdaListVO vo = new PdaListVO();
        vo.setHid(bo.getStorageHeadId());
        vo.setSno(bo.getSourceNo());
        vo.setStatus(CommonConstant.FLAG_STORAGE);

        if (null != bo.getBos() && bo.getBos().size() > 0) {
            List<String> minfo = new ArrayList<>(bo.getBos().size());
            for (StorageBodyBO bodyBO : bo.getBos()) {
                minfo.add(bodyBO.getMaterialName() + " " + bodyBO.getMaterialModel());
            }
            vo.setMinfos(minfo);
        }

        return vo;
    }
}
