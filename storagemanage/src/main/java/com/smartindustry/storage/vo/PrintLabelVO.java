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

    private Long plid;
    private String pid;
    private Date pdate;
    private String pbatch;
    private Integer num;
    private String rpid;
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
        vo.setCtime(po.getAddTime());
        return vo;
    }
}
