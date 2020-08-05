package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/17 10:32
 * @description: 出库单详情
 * @version: 1.0
 */
@Data
public class OutboundDetailVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long oid;
    private String ono;
    private String pno;
    private String cproject;
    private Date ptime;
    private List<PickBodyVO> body;

    /**
     * bo 转 vo
     *
     * @param outboundBO
     * @param labelBOs
     * @return
     */
    public static OutboundDetailVO convert(OutboundBO outboundBO, List<PrintLabelBO> labelBOs) {
        OutboundDetailVO detailVO = new OutboundDetailVO();
        detailVO.setOid(outboundBO.getOutboundId());
        detailVO.setOno(outboundBO.getOutboundNo());
        detailVO.setPno(outboundBO.getPickNo());
        detailVO.setCproject(outboundBO.getCorrespondProject());
        detailVO.setPtime(outboundBO.getPlanTime());
        List<PickBodyVO> body = new ArrayList<>(labelBOs.size());
        for (PrintLabelBO bo : labelBOs) {
            PickBodyVO vo = new PickBodyVO();
            vo.setPid(bo.getPackageId());
            vo.setMno(bo.getMaterialNo());
            vo.setMname(bo.getMaterialName());
            vo.setMdesc(bo.getMaterialDesc());
            vo.setNum(bo.getNum());
            body.add(vo);
        }
        detailVO.setBody(body);

        return detailVO;
    }
}
