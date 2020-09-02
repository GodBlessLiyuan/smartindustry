package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return pickManageService.pageQuery(reqData);
    }

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
    @PreAuthorize("@ss.hasPermi('om:pm:wopick:query')")
    public ResultVO queryPickHeadMsg(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "100000000") int pageSize,
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

    @RequestMapping("queryPickGoods")
    @PreAuthorize("@ss.hasPermi('om:pm:fppick:query')")
    public ResultVO queryPickGoods(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "100000000") int pageSize,
                                   @RequestParam(value = "pno", required = false) String pickNo,
                                   @RequestParam(value = "ono", required = false) String orderNo,
                                   @RequestParam(value = "cproject", required = false) String correspondProject,
                                   @RequestParam(value = "mstatus", defaultValue = "") Byte materialStatus) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("pickNo", pickNo);
        reqData.put("orderNo", orderNo);
        reqData.put("correspondProject", correspondProject);
        reqData.put("materialStatus", materialStatus);
        return pickManageService.queryPickGoods(pageNum, pageSize, reqData);
    }

    @RequestMapping("outOrderCheck")
    @PreAuthorize("@ss.hasPermi('om:pm:check:query')")
    public ResultVO outOrderCheck(@RequestBody Map<String, Object> reqData) {
        return pickManageService.outOrderCheck(reqData);
    }

    /**
     * 扫描拣货的欠料列表
     *
     * @param pickHeadId
     * @param materialNo
     * @param materialName
     * @return
     * @author jiangzhaojie
     */
    @RequestMapping("materialLoss")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:queryinfo,om:pm:wopick:scan,om:pm:fppick:queryinfo,om:pm:fppick:scan,om:pm:check:check,om:qm:oqctest:check')")
    public ResultVO materialLoss(@RequestParam(value = "phid", required = false) Long pickHeadId,
                                 @RequestParam(value = "mno", required = false) String materialNo,
                                 @RequestParam(value = "mname", required = false) String materialName) {
        Map<String, Object> reqData = new HashMap<>(3);
        reqData.put("pickHeadId", pickHeadId);
        reqData.put("materialNo", materialNo);
        reqData.put("materialName", materialName);
        return pickManageService.materialLoss(reqData);
    }

    /**
     * 查看当前拣货工单号的异常数据（未扫描优先推荐的pid）
     *
     * @param pickHeadId
     * @return
     */
    @RequestMapping("queryExItems")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:queryinfo,om:pm:wopick:scan,om:pm:fppick:queryinfo,om:pm:fppick:scan,om:pm:check:check,om:qm:oqctest:check')")
    public ResultVO queryExItems(@RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.queryExItems(pickHeadId);
    }

    /**
     * 拣货单打印-详情
     *
     * @param dto
     * @return
     * @author xiahui
     */
    @RequestMapping("detail")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return pickManageService.detail(dto);
    }


    @RequestMapping("pickPidOut")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:scan,om:pm:fppick:scan')")
    public ResultVO pickPidOut(@RequestParam(value = "phid", required = false) Long pickHeadId,
                               @RequestParam(value = "pid", required = false) String packageId) {
        return pickManageService.pickPidOut(pickHeadId, packageId);
    }

    @RequestMapping("queryByPhId")
    public ResultVO queryByPhId(@RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.queryByPhId(pickHeadId);
    }

    @RequestMapping("packageIdDiv")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:scan,om:pm:fppick:scan')")
    public ResultVO packageIdDiv(@RequestParam(value = "plid", required = false) Long printLabelId,
                                 @RequestParam(value = "snum", required = false) Integer num) {
        return pickManageService.packageIdDiv(printLabelId, num);
    }

    @RequestMapping("showMsgByPid")
    public ResultVO showMsgByPid(@RequestParam(value = "pid", required = false) String packageId) {
        return pickManageService.showMsgByPid(packageId);
    }

    @RequestMapping("showScanItems")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:queryinfo,om:pm:wopick:scan,om:pm:fppick:queryinfo,om:pm:fppick:scan')")
    public ResultVO showScanItems(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "100000000") int pageSize,
                                  @RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.showScanItems(pageNum, pageSize, pickHeadId);
    }

    @RequestMapping("deleteScanPid")
    public ResultVO deleteScanPid(@RequestParam(value = "phid", required = false) Long pickHeadId,
                                  @RequestParam(value = "plid", required = false) Long printLabelId) {
        return pickManageService.deleteScanPid(pickHeadId, printLabelId);
    }

    @RequestMapping("printLabelSplit")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:scan,om:pm:fppick:scan')")
    public ResultVO printLabelSplit(@RequestParam(value = "pid", required = false) String packageId) {
        return pickManageService.printLabelSplit(packageId);
    }

    @RequestMapping("judgeStatus")
    public ResultVO judgeStatus(@RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.judgeStatus(pickHeadId);
    }

    @RequestMapping("updateException")
    public ResultVO updateException(@RequestParam(value = "phid", required = false) Long pickHeadId,
                                    @RequestParam(value = "mid", required = false) Long materialId,
                                    @RequestParam(value = "exce", required = false) String exception) {
        return pickManageService.updateException(pickHeadId, materialId, exception);
    }

    @RequestMapping("outBoundItems")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:scan')")
    public ResultVO outBoundItems(@RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.outBoundItems(pickHeadId);
    }

    @RequestMapping("deleteSplit")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:wopick:scan,om:pm:fppick:scan')")
    public ResultVO deleteSplit(@RequestParam(value = "pid", required = false) String packageId) {
        return pickManageService.deleteSplit(packageId);
    }

    @RequestMapping("disAgree")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:check:check,om:qm:oqctest:check')")
    public ResultVO disAgree(@RequestParam(value = "phid", required = false) Long pickHeadId,
                             @RequestParam(value = "status", required = false) Byte status,
                             @RequestParam(value = "msg", required = false) String message) {
        return pickManageService.disAgree(pickHeadId, status, message);
    }

    @RequestMapping("agreeOutBound")
    @PreAuthorize("@ss.hasAnyPermi('om:pm:check:check,om:qm:oqctest:check')")
    public ResultVO agreeOutBound(@RequestParam(value = "phid", required = false) Long pickHeadId) {
        return pickManageService.agreeOutBound(pickHeadId);
    }


}
