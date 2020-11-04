package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.OutboundDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:23 2020/11/1
 * @ Description：成品入库controller
 * @ Modified By：
 * @ Version:     1.0
 */
@RequestMapping("storage")
@RestController
public class StorageController {
    @Autowired
    private IStorageService storageService;
    @Autowired
    private IOutboundService finishOutboundService;
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     *
     * @return
     */
    @PostMapping("generate_stockbill")
    public ResultVO generateStockbill(@RequestBody OperateDTO dto) {
        return storageService.generateStockbill(dto);
    }

    /**
     * 详细查询
     *
     * @return
     */
    @PostMapping("detail")
    public ResultVO detail(HttpSession session, @RequestBody StorageDTO dto) {
        return storageService.detail(session, dto);
    }

    /**
     * 叉车叉货时叉起调用接口
     *
     * @return
     */
  /*  @PostMapping("execute")
    public ResultVO forkliftLift(HttpSession session, @RequestBody StorageDTO dto) {
        return storageService.forkliftLift(session, dto);
    }*/
}
