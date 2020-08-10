package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.LabelRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/8 16:48
 * @description: 标签打印记录
 * @version: 1.0
 */
@Data
public class LabelRecordVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * packageID
     */
    private String pid;
    /**
     * 打印物料数
     */
    private Integer num;
    /**
     * 打印时间
     */
    private Date cdate;
    /**
     * 关联的packageID
     */
    private String rpid;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<LabelRecordVO> convert(List<LabelRecordBO> bos) {
        List<LabelRecordVO> vos = new ArrayList<>(bos.size());
        for (LabelRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static LabelRecordVO convert(LabelRecordBO bo) {
        LabelRecordVO vo = new LabelRecordVO();
        vo.setPid(bo.getPackageId());
        vo.setNum(bo.getNum());
        vo.setCdate(bo.getCreateTime());
        vo.setRpid(bo.getRelatePackageId());
        return vo;
    }
}
