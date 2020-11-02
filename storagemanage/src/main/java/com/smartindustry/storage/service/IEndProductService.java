package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:08 2020/11/1
 * @ Description：成品入库服务类
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IEndProductService {
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     * @param reqData
     * @return
     */
    ResultVO GenerateStockbill(Map<String, Object> reqData);

    /**
     * 叉车叉起货物调用接口
     * @param reqData
     * @return
     */
    ResultVO ForkliftLift(Map<String, Object> reqData);
}
