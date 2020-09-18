package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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

    private String mno;
    private String mname;
    private String mdesc;

    private List<PrintLabelVO> data = new ArrayList<>();



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


    public static List<PrintLabelVO> convertBO(List<PrintLabelBO> bos) {
        List<PrintLabelVO> vos = new ArrayList<>(bos.size());
        for (PrintLabelBO bo : bos) {
            vos.add(convertBO(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static PrintLabelVO convertBO(PrintLabelBO bo) {
        PrintLabelVO vo = new PrintLabelVO();
        vo.setPid(bo.getPackageId());
        vo.setPlid(bo.getPrintLabelId());
        vo.setPdate(bo.getProduceDate());
        vo.setPbatch(bo.getProduceBatch());
        vo.setCtime(bo.getCreateTime());
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getNum());
        return vo;
    }

    public static PrintLabelVO simpleConvertBO(PrintLabelBO bo) {
        PrintLabelVO vo = new PrintLabelVO();
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        return vo;
    }

    public static PrintLabelVO convertBO4Lable(PrintLabelBO bo) {
        PrintLabelVO vo = new PrintLabelVO();
       vo.setPid(bo.getPackageId());
       vo.setNum(bo.getNum());
        return vo;
    }

    public static List<PrintLabelVO> convertBO4Lable(List<PrintLabelBO> bos) {
        List<PrintLabelVO> vos = new ArrayList<>(bos.size());
        for (PrintLabelBO bo: bos) {
            vos.add(PrintLabelVO.convertBO4Lable(bo));
        }
        return vos;
    }

    /**
     * 用于将物料按照物料编号分组 分别查询、
     *
     * @param bos
     * @return
     */
    public static List<PrintLabelVO> convertBO4Tree(List<PrintLabelBO> bos) {
       Map<String, List<PrintLabelBO>> map = bos.stream().collect(Collectors.toMap(
               PrintLabelBO::getMaterialNo, p -> {
                    List<PrintLabelBO> bs = new ArrayList<>();
                    bs.add(p);
                    return bs;
               }, (List<PrintLabelBO> value1, List<PrintLabelBO> value2) -> {
                   value1.addAll(value2);
                   return value1;
               }
       ));
       List<PrintLabelVO> vos = new ArrayList<>(map.size());
       for (String key: map.keySet()) {
           PrintLabelVO vo = PrintLabelVO.simpleConvertBO(map.get(key).get(0));
           vo.setData(convertBO4Lable(map.get(key)));
           vo.setNum(map.get(key).stream().collect(Collectors.summingInt(PrintLabelBO::getNum)));
           vos.add(vo);
       }
        return vos;
    }

}
