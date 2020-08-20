package com.smartindustry.common.bo.si;

import com.smartindustry.common.pojo.si.BomRecordPO;
import lombok.Data;

import java.util.Date;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:40 2020/8/20
 * @version: 1.0.0
 * @description:
 */
@Data
public class BomRecordBO extends BomRecordPO {
    private static final long SerialVersionUID = 1L;

    /**
     * 操作人姓名
     */
    private String name;

    public BomRecordBO(Long bomHeadId, Long userId, Date createTime, String type, String name) {
        super(bomHeadId, userId, createTime, type);
        this.name = name;
    }
    public BomRecordBO(){}
}
