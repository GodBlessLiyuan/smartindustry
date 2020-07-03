package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.MaterialStorageBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/2 19:46
 * @description: 物料入库 分页VO
 * @version: 1.0
 */
@Data
public class StoragePageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 入库单ID
     */
    private Long sid;
    /**
     * 收料单ID
     */
    private Long rid;
    /**
     * 入库单号
     */
    private String sno;
    /**
     * 收料单号
     */
    private String rno;
    /**
     * 收料类型
     */
    private Byte rtype;
    /**
     * 物料编码
     */
    private String mno;
    /**
     * 物料类型
     */
    private Byte mtype;
    /**
     * 物料描述
     */
    private String mdesc;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 入库状态
     */
    private Byte status;
    /**
     * 待入库数
     */
    private Integer pnum;
    /**
     * 已入库数
     */
    private Integer snum;
    /**
     * 入库时间
     */
    private Date stime;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<StoragePageVO> convert(List<MaterialStorageBO> bos) {
        List<StoragePageVO> vos = new ArrayList<>();
        for (MaterialStorageBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    /**
     * bo 转 vo
     *
     * @param bo
     * @return
     */
    public static StoragePageVO convert(MaterialStorageBO bo) {
        StoragePageVO vo = new StoragePageVO();
        vo.setSid(bo.getStorageId());
        vo.setRid(bo.getReceiptBodyId());
        vo.setSno(bo.getStorageNo());
        vo.setRno(bo.getReceiptNo());
        vo.setRtype(bo.getOrderType());
        vo.setMno(bo.getMaterialNo());
        vo.setMtype(bo.getMaterialType());
        vo.setMdesc(bo.getMaterialDesc());
        vo.setCtime(bo.getCreateTime());
        vo.setStatus(bo.getStatus());
        vo.setPnum(bo.getPendingNum());
        vo.setSnum(bo.getStoredNum());
        vo.setStime(bo.getStorageTime());
        return vo;
    }
}
