package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.*;
import com.smartindustry.common.vo.ResultVO;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:42
 * @description: 基础数据
 * @version: 1.0
 */
public interface IBasicDataService {
    ResultVO wtQuery();

    ResultVO wtEdit(WarehouseTypeDTO dto);

    ResultVO wtDelete(BasicDataDTO dto);

    ResultVO sgQuery();

    ResultVO sgEdit(SupplierGroupDTO dto);

    ResultVO sgDelete(BasicDataDTO dto);

    ResultVO csQuery();

    ResultVO csEdit(CertStatusDTO dto);

    ResultVO csDelete(BasicDataDTO dto);

    ResultVO stQuery();

    ResultVO stEdit(SupplierTypeDTO dto);

    ResultVO stDelete(BasicDataDTO dto);

    ResultVO spQuery();

    ResultVO spEdit(SettlePeriodDTO dto);

    ResultVO spDelete(BasicDataDTO dto);

    ResultVO currencyQuery();

    ResultVO currencyEdit(CurrencyDTO dto);

    ResultVO currencyDelete(BasicDataDTO dto);
}
