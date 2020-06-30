package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;

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
     * @param ono
     * @param otype
     * @return
     */
    ResultVO order(String ono, Byte otype);
}
