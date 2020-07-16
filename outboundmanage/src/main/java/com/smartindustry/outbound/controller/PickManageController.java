package com.smartindustry.outbound.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IPickManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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

    @RequestMapping("queryPickHeadMsg")
    public ResultVO queryPickHeadMsg(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(value = "pickNo", required = false) String pickNo,
                                     @RequestParam(value = "orderNo", required = false) String orderNo,
                                     @RequestParam(value = "correspondProject", required = false) String correspondProject,
                                     @RequestParam(value = "materialStatus", required = false) Byte materialStatus) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("pickNo", pickNo);
        reqData.put("orderNo", orderNo);
        reqData.put("correspondProject", correspondProject);
        reqData.put("materialStatus", materialStatus);
        return pickManageService.pageQueryPickHead(pageNum, pageSize, reqData);
    }

    /**
     * 根据工单拣货通知单扫描拣货页面清单
     *
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @return
     */
    @RequestMapping("scanPick")
    public ResultVO scanPick(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                             @RequestParam(value = "pickNo", required = false) String pickNo) {
        return pickManageService.scanPick(pageNum, pageSize, pickNo);
    }

//    @RequestMapping("materialLoss")
//    public ResultVO materialLoss(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
//                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//                                 @RequestParam(value = "pickNo", required = false) String pickNo,
//                                 @RequestParam(value = "pickNo", required = false) String pickNo,
//                                 @RequestParam(value = "pickNo", required = false) String pickNo,
//                                 @RequestParam(value = "pickNo", required = false) String pickNo){
//        return pickManageService.materialLoss(pageNum,pageSize,pickNo);
//    }
}
