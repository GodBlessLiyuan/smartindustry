package com.smartindustry.basic.vo;
import com.smartindustry.common.bo.si.BomRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:53 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String name;

    private Date ctime;

    private String type;

    public static List<BomRecordVO> convert(List<BomRecordBO> bos) {
        List<BomRecordVO> vos = new ArrayList<>(bos.size());
        for (BomRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static BomRecordVO convert(BomRecordBO bo) {
        BomRecordVO vo = new BomRecordVO();
        vo.setName(bo.getName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
