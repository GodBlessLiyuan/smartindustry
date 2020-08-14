package com.smartindustry.basic.dto;

import com.smartindustry.common.mapper.dd.MaterialPropertyMapper;
import com.smartindustry.common.pojo.dd.MaterialPropertyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:10 2020/8/13
 * @version: 1.0.0
 * @description:
 */
@Data
public class PropertyDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mpid;

    private String mpname;

    public static MaterialPropertyPO createPO(PropertyDTO dto) {
        MaterialPropertyPO po = new MaterialPropertyPO();
        po.setMaterialPropertyName(dto.getMpname());
        po.setUserId(1L);
        po.setCreateTime(new Date());
        return po;
    }
}
