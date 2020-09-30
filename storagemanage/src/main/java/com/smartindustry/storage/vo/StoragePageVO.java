package com.smartindustry.storage.vo;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.sm.StorageBO;
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
     * 入库状态
     */
    private Byte status;
    /**
     * 入库时间
     */
    private Date stime;

    /**
     * 对应单号
     */
    private String cono;

    /**
     * 入库类型（调拨订单类型）
     */
    private Byte ttype;

    private Boolean flag;

    /**
     * 工单号
     */
    private String pno;

    /**
     * bos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<StoragePageVO> convert(List<StorageBO> bos) {
        List<StoragePageVO> vos = new ArrayList<>(bos.size());
        for (StorageBO bo : bos) {
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
    public static StoragePageVO convert(StorageBO bo) {
        StoragePageVO vo = new StoragePageVO();
        vo.setSid(bo.getStorageId());
        vo.setRbid(bo.getReceiptBodyId());
        vo.setSno(bo.getStorageNo());
        vo.setRno(bo.getReceiptNo());
        if(bo.getReceiptSourceNo()!=null){
            vo.setSono(bo.getReceiptSourceNo());
        }else {
            //适应其他入库单查询
            vo.setSono(bo.getSourceNo());
        }
        if (null != bo.getLocations() && bo.getLocations().size() > 0) {
           vo.setWname(bo.getLocations().get(0).getWarehouseName());
        }
        vo.setStatus(bo.getStatus());
        vo.setStime(bo.getStorageTime());
        vo.setCono(bo.getCorrespondNo());
        vo.setTtype(bo.getTransferType());
        if (bo.getLocations() != null && !bo.getLocations().isEmpty()) {
            if (bo.getLocations().get(0).getLocationId() == null) {
                vo.setFlag(false);
            } else {
                vo.setFlag(true);
            }

        } else {
            vo.setFlag(false);
        }
        vo.setPno(bo.getPickNo());
        return vo;
    }
}
