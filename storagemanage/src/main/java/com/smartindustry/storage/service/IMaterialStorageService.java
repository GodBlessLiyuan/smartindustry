package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.MaterialStorageDTO;

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
     * @param rbid
     * @param pid
     * @return
     */
    ResultVO label(Long rbid, String pid);

    /**
     * 入库
     *
     * @param dto
     * @return
     */
    ResultVO storage(MaterialStorageDTO dto) throws Exception;
}
