package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 9:12
 * @description: ERP 外部接口
 * @version: 1.0
 */
public interface IErpExternalService {
    /**
     * 获取订单信息
     *
     * @param reqData
     * @return
     */
    ResultVO order(Map<String, Object> reqData);
}
