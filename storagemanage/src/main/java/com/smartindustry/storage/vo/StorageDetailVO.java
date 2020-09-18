package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.sm.*;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
     * 收料单号
     */
    private String rno;

    /**
     * 来源单号
     */
    private String sono;
    /**
     * 仓库名称
     */
    private String wname;

    /**
     * 入库时间
     */
    private Date stime;

    /**
     * 对应单号  其他入库单
     */
    private String cono;

    /**
     * 入库类型（调拨订单类型）
     */

    private Byte ttype;

    /**
     * 工单号
     */
    private String pno;
    /**
     * 物料编码
     */
    private String mno;

    /**
     * 物料名称
     */
    private String mname;
    /**
     * 待入库数
     */
    private Integer pnum;
    /**
     * 已入库数
     */
    private Integer snum;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 新增库位的物料信息
     */
    private List<DetailVO> labels;
    /**
     * 分组数据
     */
    private List<GroupVO> group;

    /**
     * po bo 转 vo
     *
     * @param msBO
     * @param rbBO
     * @param sgBOs
     * @return
     */
    public static StorageDetailVO convert(StorageBO msBO, ReceiptBodyBO rbBO, List<StorageGroupBO> sgBOs) {
        StorageDetailVO vo = new StorageDetailVO();
        vo.setSid(msBO.getStorageId());
        vo.setRbid(msBO.getReceiptBodyId());
        vo.setSno(msBO.getStorageNo());
        vo.setMno(rbBO.getMaterialNo());
        vo.setPnum(msBO.getPendingNum());
        vo.setSnum(msBO.getStoredNum());
        vo.setStatus(msBO.getStatus());
        vo.setRno(rbBO.getReceiptNo());
        if (StringUtils.isEmpty(rbBO.getSourceNo())) {
            vo.setSono(rbBO.getSourceNo());
        } else {
            //适应其他入库单查询
            vo.setSono(msBO.getSourceNo());
        }
        vo.setTtype(msBO.getTransferType());
        vo.setPno(msBO.getPickNo());
        vo.setCono(msBO.getCorrespondNo());
        List<GroupVO> groupVOs = new ArrayList<>(sgBOs.size());
        for (StorageGroupBO sgBO : sgBOs) {
            groupVOs.add(convert(sgBO));
        }
        vo.setGroup(groupVOs);

        return vo;
    }

    /**
     * po bo 转 vo
     *
     * @param msBO
     * @param rbBO
     * @param sgBOs
     * @return
     */
    public static StorageDetailVO convert(StorageBO msBO, ReceiptBodyBO rbBO, List<StorageGroupBO> sgBOs, StorageGroupBO bo) {
        StorageDetailVO vo = convert(msBO, rbBO, sgBOs);
        vo.setLabels(convert(bo.getDetail()));
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

        vo.setDetail(convert(bo.getDetail()));
        return vo;
    }

    public static List<DetailVO> convert(List<StorageDetailBO> bos) {
        List<DetailVO> detailVOs = new ArrayList<>(bos.size());
        for (StorageDetailBO sdBO : bos) {
            detailVOs.add(convert(sdBO));
        }
        return detailVOs;
    }

    public static DetailVO convert(StorageDetailBO bo) {
        DetailVO vo = new DetailVO();
        vo.setSdid(bo.getStorageDetailId());
        vo.setPlid(bo.getPrintLabelId());
        vo.setPid(bo.getPackageId());
        vo.setMno(bo.getMaterialNo());
        vo.setMno(bo.getMaterialName());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setNum(bo.getNum());
        return vo;
    }

    public static List<StorageDetailVO> convert4Detail(List<StorageGroupDetailBO> sgBos) {
        List<StorageDetailVO> vos = new ArrayList<>(sgBos.size());
        for (StorageGroupDetailBO bo : sgBos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static StorageDetailVO convert(StorageGroupDetailBO bo) {
        StorageDetailVO vo = new StorageDetailVO();
        vo.setMno(bo.getMaterialNo());
        vo.setMname(bo.getMaterialName());
        vo.setPnum(bo.getNum());
        List<GroupVO> groupVOS = new ArrayList<>(bo.getDetail().size());
        for (StorageDetailBO dbo: bo.getDetail()) {
            groupVOS.add(convertGroup(dbo));
        }
        vo.setGroup(groupVOS);
        return vo;
    }

    public static GroupVO convertGroup(StorageDetailBO bo) {
        GroupVO vo = new GroupVO();
        vo.setLno(bo.getLocationNo());
        vo.setWhname(bo.getWarehouseName());
        List<DetailVO> vos = new ArrayList<>(bo.getLabels().size());
        for (StorageDetailBO dbo: bo.getLabels()) {
            vos.add(convert(dbo));
        }
        vo.setDetail(vos);
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
         * 仓库
         */
        private String whname;
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
         * 物料名称
         */
        private String mname;
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
