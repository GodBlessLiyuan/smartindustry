package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OperateDTO;

import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:08 2020/11/1
 * @ Description：成品入库服务类
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IStorageService {
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     * @param dto
     * @return
     */
    ResultVO GenerateStockbill(OperateDTO dto);

    /**
     * 叉车叉起货物调用接口
     * @param reqData
     * @return
     */
    ResultVO ForkliftLift(Map<String, Object> reqData);
}
