package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:41
 * @description: 物料入库
 * @version: 1.0
 */
public interface IMaterialStorageService {
    /**
     * 入库单 分页查询
     *
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 其他入库单的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQueryOther(Map<String, Object> reqData);

    /**
     * 根据入库id查询所有物料详情（待入库数量和已入库数量）
     * @param dto
     * @return
     */
    ResultVO queryInfo(OperateDTO dto);

    /**
     * 库位查询
     *
     * @param dto
     * @return
     */
    ResultVO location(@RequestBody OperateDTO dto);

    /**
     * 打印标签查询
     *
     * @return
     */
    ResultVO label(StorageGroupDTO dto);

    /**
     * 编辑
     *
     * @param dto
     * @return
     */
    ResultVO edit(StorageDetailDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    ResultVO delete(StorageDetailDTO dto);

    /**
     * 保存
     *
     * @param dto
     * @return
     */
    ResultVO save(StorageGroupDTO dto);

    /**
     * 入库
     *
     * @param dto
     * @return
     */
    ResultVO storage(@RequestBody OperateDTO dto);

    /**
     * 详情
     *
     * @param dto
     * @return
     */
    ResultVO detail(@RequestBody OperateDTO dto);

    /**
     * 查询操作记录
     *
     * @param dto
     * @return
     */
    ResultVO record(@RequestBody OperateDTO dto);

    /**
     * 扫码入库
     * @param dto
     * @return
     */
    ResultVO storageScan(@RequestBody OperateDTO dto);

    /**
     * 其他出库单同意出库
     * @param dto
     * @return
     */
    ResultVO agreeStorage(OperateDTO dto);

    /**
     * 根据入库id查询相关pid
     * @param reqData
     * @return
     */
    ResultVO queryPidInfo(Map<String, Object> reqData);
}
