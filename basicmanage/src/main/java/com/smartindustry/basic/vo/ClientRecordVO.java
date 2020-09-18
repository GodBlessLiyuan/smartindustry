package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.ClientRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:23 2020/9/17
 * @version: 1.0.0
 * @description:
 */
@Data
public class ClientRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;

    private Date ctime;

    private String type;

    public static List<ClientRecordVO> convert(List<ClientRecordBO> bos) {
        List<ClientRecordVO> vos = new ArrayList<>(bos.size());
        for (ClientRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static ClientRecordVO convert(ClientRecordBO bo) {
        ClientRecordVO vo = new ClientRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
