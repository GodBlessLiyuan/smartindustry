package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.OutboundHeadPO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:23 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class OutboundHeadBO extends OutboundHeadPO {

    private Long mixHeadId;

    private String mixNo;

    private Date planTime;

    private String clientName;

    List<OutboundBodyBO> bodyBOs;
}
