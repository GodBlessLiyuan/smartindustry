package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.OperateDTO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:02
 * @description: 质量管理
 * @version: 1.0
 */
public interface IQualityManageService {
    /**
     * 点击OQC检测的按钮
     * @param pickHeadId
     * @return
     */
    ResultVO pickOqcButton(Long pickHeadId);

    /**
     * OQC检测分页查询
     * @param pageNum
     * @param pageSize
     * @param pickNo
     * @param orderNo
     * @return
     */
    ResultVO queryOqc(int pageNum, int pageSize, String pickNo, String orderNo);

    /**
     * 查看当前的工单的操作记录
     * @param dto
     * @return
     */
    ResultVO queryRecordMsg(OperateDTO dto);

}
