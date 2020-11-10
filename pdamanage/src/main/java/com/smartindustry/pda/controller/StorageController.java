package com.smartindustry.pda.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.service.IOutboundService;
import com.smartindustry.pda.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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
    @PostMapping("gstockbill")
    public ResultVO generateStockbill(@RequestBody OperateDTO dto) {
        return storageService.generateStockbill(dto);
    }

    /**
     * mes打包后进行rfid和表头进行绑定
     *
     * @return
     */
    @PostMapping("rfidbound")
    public ResultVO rfidBound(@RequestBody OperateDTO dto) {
        return storageService.rfidBound(dto);
    }

    /**
     * mes打包后进行rfid和表头进行绑定
     *
     * @return
     */
    @PostMapping("rfidbshid")
    public ResultVO rfidBoundStorageHeadId(@RequestBody StorageDTO dto) {
        return storageService.rfidBoundStorageHeadId(dto);
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
     * 叉车叉刚生产出货时叉起调用接口
     *
     * @return
     */
    @PostMapping("execute")
    public ResultVO execute(HttpSession session, @RequestBody StorageDTO dto) {
        return storageService.execute(session, dto.getMrfid());
    }

    /**
     * 叉车叉备料区实时展示接口
     *
     * @return
     */
    @PostMapping("efpre")
    public ResultVO executeForPre(HttpSession session, @RequestBody StorageDTO dto) {
        return storageService.executeForPre(session, dto.getMrfid());
    }

    /**
     * 备料区选择产品的弹窗接口
     *
     * @return
     */
    @PostMapping("cmshow")
    public ResultVO chooseMaterialShow(@RequestBody StorageDTO dto) {
        return storageService.chooseMaterialShow(dto.getMrfid());
    }

    /**
     * 备料区选择产品后调用接口
     *
     * @return
     */
    @PostMapping("cmconfirm")
    public ResultVO chooseMaterialComfirm(HttpSession session, @RequestBody StorageDTO dto) {
        return storageService.chooseMaterialConfirm(session, dto);
    }
}
