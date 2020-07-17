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

//    /**
//     * 根据工单拣货通知单扫描拣货页面清单
//     *
//     * @param pageNum
//     * @param pageSize
//     * @param pickNo
//     * @return
//     * @author jiangzhaojie
//     */
//    @RequestMapping("scanPick")
//    public ResultVO scanPick(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
//                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
//                             @RequestParam(value = "pickNo", required = false) String pickNo) {
//        return pickManageService.scanPick(pageNum, pageSize, pickNo);
//    }

    /**
     * 扫描拣货的欠料列表
     *
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @param materialNo
     * @param materialName
     * @param flag
     * @return
     * @author jiangzhaojie
     */
    @RequestMapping("materialLoss")
    public ResultVO materialLoss(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "pickNo", required = false) String pickNo,
                                 @RequestParam(value = "materialNo", required = false) String materialNo,
                                 @RequestParam(value = "materialName", required = false) String materialName,
                                 @RequestParam(value = "flag", required = false) Byte flag) {
        Map<String, Object> reqData = new HashMap<>(4);
        reqData.put("pickNo", pickNo);
        reqData.put("orderNo", materialNo);
        reqData.put("correspondProject", materialName);
        reqData.put("materialStatus", flag);
        return pickManageService.materialLoss(pageNum, pageSize, reqData);
    }

    /**
     * 查看当前拣货工单号的异常数据（未扫描优先推荐的pid）
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @return
     */
    @RequestMapping("queryExItems")
    public ResultVO queryExItems(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                 @RequestParam(value = "pickNo", required = false) String pickNo){
        return pickManageService.queryExItems(pageNum, pageSize, pickNo);
    }

}
