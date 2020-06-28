package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.RecordPO;
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

    private String name;
    private String type;
    private Date cTime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<RecordVO> convert(List<RecordPO> pos) {
        List<RecordVO> vos = new ArrayList<>();
        for (RecordPO po : pos) {
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
    public static RecordVO convert(RecordPO po) {
        RecordVO vo = new RecordVO();
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setCTime(po.getCreateTime());
        return vo;
    }
}
