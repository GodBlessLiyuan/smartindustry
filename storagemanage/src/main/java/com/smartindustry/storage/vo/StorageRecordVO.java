package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.sm.StorageRecordPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:21 2020/10/29
 * @version: 1.0.0
 * @description:
 */
@Data
public class StorageRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 操作名称
     */
    private String type;

    public static List<StorageRecordVO> convert(List<StorageRecordPO> pos) {
        List<StorageRecordVO> vos = new ArrayList<>(pos.size());
        for (StorageRecordPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static StorageRecordVO convert(StorageRecordPO po) {
        StorageRecordVO vo = new StorageRecordVO();
        vo.setName("admin");
        vo.setCtime(po.getCreateTime());
        vo.setType(po.getOperationName());
        return vo;
    }
}
