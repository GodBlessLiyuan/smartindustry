package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 18:58
 * @description: 拣货单管理
 * @version: 1.0
 */
@RequestMapping("pick")
@RestController
public class PickManageController {
    @Autowired
    private IPickManageService pickManageService;

    /**
     * 工单拣货单状态查询
     *
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @param orderNo
     * @param correspondProject
     * @param materialStatus
     * @return
     * @author jiangzhaojie
     */
    @RequestMapping("queryPick")
    public ResultVO queryPickHeadMsg(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pno", required = false) String pickNo,
                                     @RequestParam(value = "ono", required = false) String orderNo,
                                     @RequestParam(value = "cproject", required = false) String correspondProject,
                                     @RequestParam(value = "mstatus", defaultValue = "") Byte materialStatus) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("pickNo", pickNo);
        reqData.put("orderNo", orderNo);
        reqData.put("correspondProject", correspondProject);
        reqData.put("materialStatus", materialStatus);
        return pickManageService.pageQueryPickHead(pageNum, pageSize, reqData);
    }

    /**
     * 扫描拣货的欠料列表
     *
     * @param pageNum
     * @param pageSize
     * @param pickHeadId
     * @param materialNo
     * @param materialName
     * @return
     * @author jiangzhaojie
     */
    @RequestMapping("materialLoss")
    public ResultVO materialLoss(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "phid", required = false) Long pickHeadId,
                                 @RequestParam(value = "mno", required = false) String materialNo,
                                 @RequestParam(value = "mname", required = false) String materialName) {
        Map<String, Object> reqData = new HashMap<>(3);
        reqData.put("pickHeadId", pickHeadId);
        reqData.put("materialNo", materialNo);
        reqData.put("materialName", materialName);
        return pickManageService.materialLoss(pageNum, pageSize, reqData);
    }

    /**
     * 查看当前拣货工单号的异常数据（未扫描优先推荐的pid）
     *
     * @param pageNum
     * @param pageSize
     * @param pickHeadId
     * @return
     */
    @RequestMapping("queryExItems")
    public ResultVO queryExItems(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.queryExItems(pageNum, pageSize, pickHeadId);
    }

    /**
     * 拣货单打印-详情
     *
     * @param phId
     * @return
     * @author xiahui
     */
    @RequestMapping("detail")
    public ResultVO detail(@RequestParam("phid") Long phId) {
        return pickManageService.detail(phId);
    }


    @RequestMapping("pickPidOut")
    public ResultVO pickPidOut(@RequestParam(value = "phid", required = false) Long pickHeadId,
                               @RequestParam(value = "pid", required = false) String packageId){
        return pickManageService.pickPidOut(pickHeadId,packageId);
    }

    @RequestMapping("queryByPhId")
    public ResultVO queryByPhId(@RequestParam(value = "phid", required = false) Long pickHeadId){
        return pickManageService.queryByPhId(pickHeadId);
    }

    @RequestMapping("packageIdDiv")
    public ResultVO packageIdDiv(@RequestParam(value = "plid", required = false) Long printLabelId,
                                 @RequestParam(value = "snum", required = false) Integer num){
        return pickManageService.packageIdDiv(printLabelId,num);
    }

    @RequestMapping("showMsgByPid")
    public ResultVO showMsgByPid(@RequestParam(value = "pid", required = false) String packageId){
        return pickManageService.showMsgByPid(packageId);
    }

    @RequestMapping("showScanItems")
    public ResultVO showScanItems(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  @RequestParam(value = "phid", required = false) Long pickHeadId){
        return pickManageService.showScanItems(pageNum,pageSize,pickHeadId);
    }

    @RequestMapping("deleteScanPid")
    public ResultVO deleteScanPid(@RequestParam(value = "phid", required = false) Long pickHeadId,
                                  @RequestParam(value = "plid", required = false) Long printLabelId){
        return pickManageService.deleteScanPid(pickHeadId,printLabelId);
    }

    @RequestMapping("printLabelSplit")
    public ResultVO printLabelSplit(@RequestParam(value = "pid", required = false) String packageId){
        return pickManageService.printLabelSplit(packageId);
    }


}
