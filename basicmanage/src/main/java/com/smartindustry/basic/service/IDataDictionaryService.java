package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.*;
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

    ResultVO cQuery();

    ResultVO cEdit(CurrencyDTO dto);

    ResultVO cDelete(BasicDataDTO dto);

    ResultVO mtQuery();

    ResultVO mtEdit(MaterialTypeDTO dto);

    ResultVO mtDelete(BasicDataDTO dto);

    ResultVO hlQuery();

    ResultVO hlEdit(HumidityLevelDTO dto);

    ResultVO hlDelete(BasicDataDTO dto);

    ResultVO mlQuery();

    ResultVO mlEdit(MaterialLevelDTO dto);

    ResultVO mlDelete(BasicDataDTO dto);

    ResultVO muQuery();

    ResultVO muEdit(MeasureUnitDTO dto);

    ResultVO muDelete(BasicDataDTO dto);

    ResultVO mvQuery();

    ResultVO mvEdit(MaterialVersionDTO dto);

    ResultVO mvDelete(BasicDataDTO dto);

    ResultVO pllQuery();

    ResultVO pllEdit(ProduceLossLevelDTO dto);

    ResultVO pllDelete(BasicDataDTO dto);

    ResultVO lcsQuery();

    ResultVO lcsEdit(LifeCycleStateDTO dto);

    ResultVO lcsDelete(BasicDataDTO dto);
}