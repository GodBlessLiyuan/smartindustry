package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.MaterialDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageHeadDTO;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:52 2020/10/26
 * @version: 1.0.0
 * @description:
 */
public interface IPurchaseStorageService {
    /**
     * 采购入库的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 采购入库单编辑
     * @param dto
     * @return
     */
    ResultVO edit(StorageHeadDTO dto);

    /**
     * 查询所有的储位信息
     * @return
     */
    ResultVO queryLocation();

    /**
     * 物料的分页查询
     * @param reqData
     * @return
     */
    ResultVO queryMaterial(Map<String, Object> reqData);

    /**
     * 采购单表体的批量删除
     * @param dto
     * @return
     */
    ResultVO deleteBody(OperateDTO dto);

    /**
     * 查询采购拣货单的信息通过shid
     * @param dto
     * @return
     */
    ResultVO detail(OperateDTO dto);
}
