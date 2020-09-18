package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.sm.StorageDetailBO;
import com.smartindustry.common.bo.sm.StorageGroupDetailBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
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
        List<GroupVO> group = new ArrayList<>(bo.getDetail().size());
        for (StorageDetailBO dbo: bo.getDetail()) {
            group.add(convertGroup(dbo));
        }
        vo.setGroup(group);
        return vo;
    }

    public static GroupVO convertGroup(StorageDetailBO bo) {
        GroupVO vo = new GroupVO();
        vo.setLno(bo.getLocationNo());
        vo.setWhname(bo.getWarehouseName());
        List<DetailVO> details = new ArrayList<>(bo.getLabels().size());
        for (StorageDetailBO dbo: bo.getLabels()) {
            details.add(convertDetail(dbo, bo.getWarehouseName(),bo.getLocationNo()));
        }
        vo.setDetail(details);
        return vo;
    }

    public static DetailVO convertDetail(StorageDetailBO bo, String mname, String lno) {
        DetailVO vo = new DetailVO();
        vo.setPid(bo.getPackageId());
        vo.setNum(bo.getNum());
        vo.setMname(mname);
        vo.setLno(lno);
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
            vo.setDetails(convertDetail(map.get(key)));
            vos.add(vo);
        }
        return vos;
    }

    public static List<DetailVO> convertDetail(List<PrintLabelBO> bos) {
        List<DetailVO> details = new ArrayList<>(bos.size());
        for (PrintLabelBO bo: bos) {
            details.add(new DetailVO(bo.getPackageId(), bo.getNum()));
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

        public DetailVO() {}

        public DetailVO(String pid, Integer num) {
            this.pid = pid;
            this.num = num;
        }
    }
}
