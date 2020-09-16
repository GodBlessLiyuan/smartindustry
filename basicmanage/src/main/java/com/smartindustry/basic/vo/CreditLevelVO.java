package com.smartindustry.basic.vo;

import com.smartindustry.common.pojo.dd.CreditLevelPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:23 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@Data
public class CreditLevelVO {
    private static final long SerialVersionUID = 1L;

    private Long clid;

    private String clname;

    public static List<CreditLevelVO> convert(List<CreditLevelPO> pos) {
        List<CreditLevelVO> vos = new ArrayList<>(pos.size());
        for (CreditLevelPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static CreditLevelVO convert(CreditLevelPO po) {
        CreditLevelVO vo = new CreditLevelVO();
        vo.setClid(po.getCreditLevelId());
        vo.setClname(po.getCreditLevelName());
        return vo;
    }
}
