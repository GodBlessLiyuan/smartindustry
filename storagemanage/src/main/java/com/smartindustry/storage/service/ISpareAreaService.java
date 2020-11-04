package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.StoragePreDTO;

import javax.servlet.http.HttpSession;

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
    ResultVO enterSpare(StoragePreDTO dto, HttpSession session);

    /**
     * 备料区入库提示
     * @param dto
     * @return
     */
    ResultVO chooseMaterial(StoragePreDTO dto);

    /**
     * 展示当前备料区入成品区的提示
     * @param dto
     * @return
     */
    ResultVO showSpare(StoragePreDTO dto);
}
