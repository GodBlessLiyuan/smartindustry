package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.StoragePreDTO;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:26 2020/11/2
 * @version: 1.0.0
 * @description:
 */
public interface ISpareAreaService {
    /**
     * 生产入库单进入备料区
     * @param dto
     * @return
     */
    ResultVO enterSpare(StoragePreDTO dto);
}
