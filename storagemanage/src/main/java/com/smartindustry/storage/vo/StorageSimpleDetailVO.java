package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.StorageDetailBO;
import com.smartindustry.common.bo.sm.StorageGroupDetailBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hui.feng
 * @date created in 2020/9/18
 * @description
 */
@Data
public class StorageSimpleDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mno;

    private String mname;

    private Integer num;

    private String nummunit;

    private List<GroupVO> group;

    private List<DetailVO> details;

    public static List<StorageSimpleDetailVO> convert(List<StorageGroupDetailBO> bos) {
        List<StorageSimpleDetailVO> vos = new ArrayList<>(bos.size());
        for (StorageGroupDetailBO bo: bos){
            vos.add(convert(bo));
        }
        return vos;
    }

    public static StorageSimpleDetailVO convert(StorageGroupDetailBO bo) {
        StorageSimpleDetailVO vo = new StorageSimpleDetailVO();
        vo.setMname(bo.getMaterialName());
        vo.setMno(bo.getMaterialNo());
        vo.setNum(bo.getNum());
        if (bo.getNum() != null) {
            vo.setNummunit(bo.getNum()+" "+(bo.getMeasureUnitName() != null ? bo.getMeasureUnitName():""));
        }
        List<DetailVO> details = new ArrayList<>(bo.getDetail().size());
        for (StorageDetailBO dbo: bo.getDetail()) {
            dbo.setMeasureUnitName(bo.getMeasureUnitName());
            details.add(convert(dbo));
        }
        if (!details.isEmpty()) {
            details.sort(Comparator.comparing(p -> p.getLno() == null? "": p.getLno()));
        }

        vo.setDetails(details);
        return vo;
    }

    public static DetailVO convert(StorageDetailBO bo) {
        DetailVO vo = new DetailVO();
        vo.setPid(bo.getPackageId());
        vo.setLno(bo.getLocationNo());
        vo.setNum(bo.getNum());
        vo.setWhname(bo.getWarehouseName());
        if (bo.getNum() != null) {
            vo.setNummunit(bo.getNum()+" "+(bo.getMeasureUnitName() != null?bo.getMeasureUnitName():""));
        }
        return vo;
    }



    public static List<StorageSimpleDetailVO> convertLabel(List<PrintLabelBO> labels) {
        Map<String, List<PrintLabelBO>> map = labels.stream().collect(Collectors.toMap(
                p -> p.getMaterialNo()+"_"+p.getMaterialName(),
                p -> {
                    List<PrintLabelBO> bs = new ArrayList<>();
                    bs.add(p);
                    return bs;
                }, (List<PrintLabelBO> value1, List<PrintLabelBO> value2) -> {
                    value1.addAll(value2);
                    return value1;
                }
        ));
        List<StorageSimpleDetailVO> vos = new ArrayList<>(map.size());
        for (String key : map.keySet()) {
            StorageSimpleDetailVO vo = new  StorageSimpleDetailVO();
            String[] keys = key.split("_");
            vo.setMno(keys[0]);
            vo.setMname(keys[1]);
            vo.setNum(map.get(key).stream().collect(Collectors.summingInt(PrintLabelBO::getNum)));
            vo.setNummunit(vo.getNum()+" "+map.get(key).get(0).getMeasureUnitName());
            vo.setDetails(convertDetail(map.get(key)));
            vos.add(vo);
        }
        return vos;
    }

    public static List<DetailVO> convertDetail(List<PrintLabelBO> bos) {
        List<DetailVO> details = new ArrayList<>(bos.size());
        for (PrintLabelBO bo: bos) {
            details.add(new DetailVO(bo.getPackageId(), bo.getNum(), bo.getMeasureUnitName()));
        }
        return details;
    }



    @Data
    private static class GroupVO {
        private String lno;

        private String whname;

        List<DetailVO> detail;
    }

    @Data
    private static class DetailVO {
        private String pid;

        private Integer num;

        private String lno;

        private String mname;

        private String whname;

        private String nummunit;

        public DetailVO() {}

        public DetailVO(String pid, Integer num, String measureUnitName) {
            this.pid = pid;
            this.num = num;
            this.nummunit = num+" "+measureUnitName;
        }
    }
}
