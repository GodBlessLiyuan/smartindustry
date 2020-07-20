package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.dto.StorageGroupDTO;

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
     * @param pageNum
     * @param pageSize
     * @param reqData
     * @return
     */
    ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData);

    /**
     * 库位查询
     *
     * @param lno
     * @return
     */
    ResultVO location(String lno);

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
     * @param sid
     * @return
     */
    ResultVO storage(Long sid);

    /**
     * 详情
     *
     * @param sid
     * @return
     */
    ResultVO detail(Long sid);

    /**
     * 查询操作记录
     *
     * @param sid
     * @return
     */
    ResultVO record(Long sid);
}
