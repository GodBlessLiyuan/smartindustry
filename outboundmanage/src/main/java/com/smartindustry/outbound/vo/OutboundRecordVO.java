package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.om.OutboundRecordBO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 21:32 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 操作名称
     */
    private String type;

    public static List<OutboundRecordVO> convert(List<OutboundRecordBO> bos) {
        List<OutboundRecordVO> vos = new ArrayList<>(bos.size());
        for (OutboundRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static OutboundRecordVO convert(OutboundRecordBO bo) {
        OutboundRecordVO vo = new OutboundRecordVO();
        if(bo.getName()!=null){
            vo.setName(bo.getName());
        }else {
            vo.setName("系统");
        }
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getOperationName());
        return vo;
    }
}
