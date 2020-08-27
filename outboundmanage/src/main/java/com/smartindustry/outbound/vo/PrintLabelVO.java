package com.smartindustry.outbound.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 18:07 2020/7/27
 * @version: 1.0.0
 * @description:
 */
@Data
public class PrintLabelVO {
    private static final long SerialVersionUID = 1L;
    private String pid;
    private Integer num;
    private String rpid;
    private Date ctime;


    public static List<PrintLabelVO> convert(List<PrintLabelPO> pos) {
        List<PrintLabelVO> vos = new ArrayList<>(pos.size());
        for (PrintLabelPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static PrintLabelVO convert(PrintLabelPO po) {
        PrintLabelVO vo = new PrintLabelVO();
        vo.setPid(po.getPackageId());
        vo.setRpid(po.getRelatePackageId());
        vo.setCtime(po.getCreateTime());
        vo.setNum(po.getNum());
        return vo;
    }
}
