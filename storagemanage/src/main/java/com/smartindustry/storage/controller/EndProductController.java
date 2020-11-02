package com.smartindustry.storage.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IEndProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:23 2020/11/1
 * @ Description：成品入库controller
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("product_storage")
@RestController
public class EndProductController {
    @Autowired
    private IEndProductService endProductService;
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     *
     * @return
     */
    @PostMapping("generate_stockbill")
    public ResultVO GenerateStockbill(@RequestBody Map<String, Object> reqData) {
        return endProductService.GenerateStockbill(reqData);
    }

    /**
     * 叉车叉货时叉起调用接口
     *
     * @return
     */
    @PostMapping("forklift_lift")
    public ResultVO ForkliftLift(@RequestBody Map<String, Object> reqData) {
        return endProductService.GenerateStockbill(reqData);
    }
}
