package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.dto.StoragePreDTO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:08 2020/11/1
 * @ Description：成品入库服务类
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IStorageService {
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     *
     * @param dto
     * @return
     */
    ResultVO generateStockbill(OperateDTO dto);

    /**
     * mes系统打包时将rfid和入库单号绑定
     *
     * @param dto
     * @return
     */
    ResultVO rfidBound(OperateDTO dto);

    /**
     * 入库单详情
     *
     * @param dto
     * @return
     */
    ResultVO detail(HttpSession session, StorageDTO dto);

    /**
     * 叉车插货物动作
     *
     * @param dto
     * @return
     */
    ResultVO execute(HttpSession session, StorageDTO dto);

    /**
     * 叉车插货物动作
     *
     * @param dto
     * @return
     */
    ResultVO executeForPre(HttpSession session, StorageDTO dto);

    /**
     * 备料区入库提示选择消息弹窗接口
     *
     * @param dto
     * @return
     */
    ResultVO chooseMaterialShow(HttpSession session, StorageDTO dto);

    /**
     * 备料区入库提示选择那种物料确认
     *
     * @param dto
     * @return
     */
    ResultVO chooseMaterialConfirm(HttpSession session, StorageDTO dto);

}
