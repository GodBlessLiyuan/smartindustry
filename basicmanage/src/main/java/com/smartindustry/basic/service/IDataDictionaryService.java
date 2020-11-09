package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.BasicDataDTO;
import com.smartindustry.basic.dto.LocationTypeDTO;
import com.smartindustry.basic.dto.MeasureUnitDTO;
import com.smartindustry.basic.dto.WarehouseTypeDTO;
import com.smartindustry.common.vo.ResultVO;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:42
 * @description: 基础数据
 * @version: 1.0
 */
public interface IDataDictionaryService {
    ResultVO wtQuery();

    ResultVO wtEdit(WarehouseTypeDTO dto);

    ResultVO wtDelete(BasicDataDTO dto);

    ResultVO ltQuery();

    ResultVO ltEdit(LocationTypeDTO dto);

    ResultVO ltDelete(BasicDataDTO dto);

    ResultVO muQuery();

    ResultVO muEdit(MeasureUnitDTO dto);

    ResultVO muDelete(BasicDataDTO dto);
}
