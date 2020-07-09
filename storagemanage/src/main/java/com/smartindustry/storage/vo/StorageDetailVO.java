package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.ReceiptBodyBO;
import com.smartindustry.common.bo.sm.StorageDetailBO;
import com.smartindustry.common.bo.sm.StorageGroupBO;
import com.smartindustry.common.pojo.sm.MaterialStoragePO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/8 19:35
 * @description: 物料入库详细VO
 * @version: 1.0
 */
@Data
public class StorageDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 入库单ID
     */
    private Long sid;
    /**
     * 收料单ID
     */
    private Long rbid;
    /**
     * 入库单号
     */
    private String sno;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 待入库数
     */
    private Integer pnum;
    /**
     * 已入库数
     */
    private Integer snum;
    /**
     * 分组数据
     */
    private List<GroupVO> group;

    /**
     * po bo 转 vo
     *
     * @param msPO
     * @param rbBO
     * @param sgBOs
     * @return
     */
    public static StorageDetailVO convert(MaterialStoragePO msPO, ReceiptBodyBO rbBO, List<StorageGroupBO> sgBOs) {
        StorageDetailVO vo = new StorageDetailVO();
        vo.setSid(msPO.getStorageId());
        vo.setRbid(msPO.getReceiptBodyId());
        vo.setSno(msPO.getStorageNo());
        vo.setMno(rbBO.getMaterialNo());
        vo.setPnum(msPO.getPendingNum());
        vo.setSnum(msPO.getStoredNum());

        List<GroupVO> groupVOs = new ArrayList<>(sgBOs.size());
        for (StorageGroupBO sgBO : sgBOs) {
            groupVOs.add(convert(sgBO));
        }
        vo.setGroup(groupVOs);

        return vo;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static GroupVO convert(StorageGroupBO bo) {
        GroupVO vo = new GroupVO();
        vo.setSgid(bo.getStorageGroupId());
        vo.setLno(bo.getLocationNo());
        List<DetailVO> detailVOs = new ArrayList<>(bo.getDetail().size());
        for (StorageDetailBO sdBO : bo.getDetail()) {
            detailVOs.add(convert(sdBO));
        }
        vo.setDetail(detailVOs);
        return vo;
    }

    public static DetailVO convert(StorageDetailBO bo) {
        DetailVO vo = new DetailVO();
        vo.setSdid(bo.getStorageDetailId());
        vo.setPlid(bo.getPrintLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getNum());
        return vo;
    }

    @Data
    private static class GroupVO {
        /**
         * 分组ID
         */
        private Long sgid;
        /**
         * 库位
         */
        private String lno;
        /**
         * 详情数据
         */
        private List<DetailVO> detail = new ArrayList<>();
    }

    @Data
    private static class DetailVO {
        /**
         * 详情ID
         */
        private Long sdid;
        /**
         * 标签ID
         */
        private Long plid;
        /**
         * 标签PID
         */
        private String pid;
        /**
         * 物料编码
         */
        private String mno;
        /**
         * 物料描述
         */
        private String mdesc;
        /**
         * 数量
         */
        private Integer num;
    }
}
