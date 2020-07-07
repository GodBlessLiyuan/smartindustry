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

    /**
     * 打印标签ID
     */
    private Long plid;
    /**
     * packageID
     */
    private String pid;
    /**
     * 生产日期
     */
    private String pdate;
    /**
     * 生产批号
     */
    private String pbatch;
    /**
     * 打印物料数
     */
    private Integer num;
    /**
     * 关联的packageID
     */
    private String rpid;
    /**
     * 打印时间
     */
    private Date ctime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<PrintLabelVO> convert(List<PrintLabelPO> pos) {
        List<PrintLabelVO> vos = new ArrayList<>(pos.size());
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
        vo.setPlid(po.getPrintLabelId());
        vo.setPid(po.getPackageId());
        vo.setPdate(po.getProduceDate());
        vo.setPbatch(po.getProduceBatch());
        vo.setNum(po.getNum());
        vo.setRpid(po.getRelatePackageId());
        vo.setCtime(po.getCreateTime());
        return vo;
    }
}
