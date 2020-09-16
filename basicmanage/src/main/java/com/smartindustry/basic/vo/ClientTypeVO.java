package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.dd.ClientTypePO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:21 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientTypeVO {
    private static final long SerialVersionUID = 1L;

    private Long ctid;

    private String ctname;

    public static List<ClientTypeVO> convert(List<ClientTypePO> pos) {
        List<ClientTypeVO> vos = new ArrayList<>(pos.size());
        for (ClientTypePO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static ClientTypeVO convert(ClientTypePO po) {
        ClientTypeVO vo = new ClientTypeVO();
        vo.setCtid(po.getClientTypeId());
        vo.setCtname(po.getClientTypeName());
        return vo;
    }
}
