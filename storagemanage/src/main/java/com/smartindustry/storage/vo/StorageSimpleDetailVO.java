package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.StorageDetailBO;
import com.smartindustry.common.bo.sm.StorageGroupBO;
import com.smartindustry.common.bo.sm.StorageGroupDetailBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
            details.add(convertDetail(dbo));
        }
        vo.setDetail(details);
        return vo;
    }

    public static DetailVO convertDetail(StorageDetailBO bo) {
        DetailVO vo = new DetailVO();
        vo.setPid(bo.getPackageId());
        vo.setNum(bo.getNum());
        return vo;
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
    }
}
