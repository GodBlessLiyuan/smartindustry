package com.smartindustry.storage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/24 10:57
 * @description: 操作记录
 * @version: 1.0
 */
@Data
public class RecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作人
     */
    private String name;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 操作日期
     */
    private Date ctime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<RecordVO> convert(List<StorageRecordPO> pos) {
        List<RecordVO> vos = new ArrayList<>(pos.size());
        for (StorageRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static RecordVO convert(StorageRecordPO po) {
        RecordVO vo = new RecordVO();
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setCtime(po.getCreateTime());
        return vo;
    }
}
