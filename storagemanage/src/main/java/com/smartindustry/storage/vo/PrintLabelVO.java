package com.smartindustry.storage.vo;

import com.smartindustry.common.pojo.PrintLabelPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 9:00
 * @description: 打印标签
 * @version: 1.0
 */
@Data
public class PrintLabelVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String packageId;
    private Integer num;
    private String linkedId;
    private Date ctime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<PrintLabelVO> convert(List<PrintLabelPO> pos) {
        List<PrintLabelVO> vos = new ArrayList<>();
        for (PrintLabelPO po : pos) {
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
    public static PrintLabelVO convert(PrintLabelPO po) {
        PrintLabelVO vo = new PrintLabelVO();
        vo.setPackageId(po.getPackageId());
        vo.setNum(po.getNum());
        vo.setLinkedId(po.getRelatePackageId());
        vo.setCtime(po.getAddTime());
        return vo;
    }
}
