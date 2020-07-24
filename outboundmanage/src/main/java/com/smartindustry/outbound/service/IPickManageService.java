package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
     * 查看成品拣货单
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO queryPickGoods(int pageNum, int pageSize, Map<String, Object> reqMap);

    /**
     * 根据工单拣货通知单的编号查询所有的物料现状(需求数，已扫描数，欠料数)
     *
     * @param reqMap
     * @return
     */
    ResultVO materialLoss(Map<String, Object> reqMap);

    /**
     * 查询当前工单拣货单的异常数据清单
     *
     * @param pickHeadId
     * @return
     */
    ResultVO queryExItems(Long pickHeadId);

    /**
     * 拣货单打印-详情
     *
     * @param phId
     * @return
     */
    ResultVO detail(Long phId);

    /**
     * 根据当前选中的工单拣货单和扫码入库的PID进行物料入库
     * @param pickHeadId
     * @param packageId
     * @return
     */
    ResultVO pickPidOut(Long pickHeadId,String packageId);

    /**
     * 根据工单拣货单id查询
     * @param pickHeadId
     * @return
     */
    ResultVO queryByPhId(Long pickHeadId);

    /**
     * 根据PID进行分料
     * @param printLabelId
     * @param num
     * @return
     */
    ResultVO packageIdDiv(Long printLabelId,Integer num);

    /**
     * 根据已有的PID进行对应的物料信息基本查询
     * @param packageId
     * @return
     */
    ResultVO showMsgByPid(String packageId);

    /**
     * 在扫码拣货下的列表展示
     * @param pageNum
     * @param pageSize
     * @param pickHeadId
     * @return
     */
    ResultVO showScanItems(int pageNum, int pageSize,Long pickHeadId);

    /**
     * 扫码拣货列表的删除
     * @param pickHeadId
     * @param printLabelId
     * @return
     */
    ResultVO deleteScanPid(Long pickHeadId, Long printLabelId);

    /**
     * 拆分之后的标签打印
     * @param packageId
     * @return
     */
    ResultVO printLabelSplit(String packageId);

    /**
     * 判断当前的物料状态
     * @param pickHeadId
     * @return
     */
    ResultVO judgeStatus(Long pickHeadId);

    /**
     * 插入工单物料的异常情况
     * @param pickHeadId
     * @param materialNo
     * @param exception
     * @return
     */
    ResultVO updateException(Long pickHeadId,String materialNo,String exception);

    /**
     * 根据当前工单拣货单id 生成 出库单
     * @param pickHeadId
     * @return
     */
    ResultVO outBoundItems(Long pickHeadId);

    /**
     * 删除分料后的PID单，恢复原来的标签打印单
     * @param packageId
     * @return
     */
    ResultVO deleteSplit(String packageId);

    /**
     * 驳回
     * @param pickHeadId
     * @param message
     * @return
     */
    ResultVO disAgree(Long pickHeadId,Byte status,String message);

    /**
     * 欠料同意出库
     * @param pickHeadId
     * @return
     */
    ResultVO agreeOutBound(Long pickHeadId);

    /**
     * 欠料出库单审核
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO outOrderCheck(int pageNum, int pageSize, Map<String, Object> reqMap);

}
