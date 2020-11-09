package com.smartindustry.common.bo.sm.bo.ds;

import com.smartindustry.common.pojo.ds.SalesOutboundPO;
import lombok.Data;

import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/11/6
 * @description
 */
@Data
public class SalesOutboundBO extends SalesOutboundPO {

    private String clientName;

    private String userName;

    private List<SalesOutboundDetailBO> details;
}
