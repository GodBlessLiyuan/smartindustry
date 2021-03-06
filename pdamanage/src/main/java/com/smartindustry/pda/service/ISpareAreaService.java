package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.StoragePreDTO;


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


}
