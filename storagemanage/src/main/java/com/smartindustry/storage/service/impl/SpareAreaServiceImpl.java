package com.smartindustry.storage.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.StoragePreDTO;
import com.smartindustry.storage.service.ISpareAreaService;
import org.springframework.stereotype.Service;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:27 2020/11/2
 * @version: 1.0.0
 * @description:
 */
@Service
public class SpareAreaServiceImpl implements ISpareAreaService {

    @Override
    public ResultVO enterSpare(StoragePreDTO dto){
        return ResultVO.ok();
    }
}
