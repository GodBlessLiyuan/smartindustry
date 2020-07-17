package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:01
 * @description: 拣货单管理
 * @version: 1.0
 */
public interface IPickManageService {
    /**
     * 根据拣货单号，工单号，对应项目，物料状态检索部分拣货单表头信息
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap   检索条件
     * @return
     */
    ResultVO pageQueryPickHead(int pageNum, int pageSize, Map<String, Object> reqMap);

    /**
     * 根据工单拣货通知单的编号查询所有的物料现状(需求数，已扫描数，欠料数)
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO materialLoss(int pageNum, int pageSize, Map<String, Object> reqMap);

    /**
     * 查询当前工单拣货单的异常数据清单
     *
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @return
     */
    ResultVO queryExItems(int pageNum, int pageSize, String pickNo);

    /**
     * 拣货单打印-详情
     *
     * @param phId
     * @return
     */
    ResultVO detail(Long phId);

    /**
     * 根据当前选中的工单拣货单和扫码入库的PID进行物料入库
     * @param pickNo
     * @param packageId
     * @return
     */
    ResultVO pickPidOut(String pickNo,String packageId);
}
