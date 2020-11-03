package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.dd.MaterialPropertyPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:00 2020/8/13
 * @version: 1.0.0
 * @description: 物料属性
 */
@Data
public class PropertyVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long mpid;

    private String mpname;

    public static List<PropertyVO> convert(List<MaterialPropertyPO> pos) {
        List<PropertyVO> vos = new ArrayList<>(pos.size());
        for (MaterialPropertyPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static PropertyVO convert(MaterialPropertyPO po) {
        PropertyVO vo = new PropertyVO();
        vo.setMpid(po.getMaterialPropertyId());
        vo.setMpname(po.getMaterialPropertyName());
        return vo;
    }
}
