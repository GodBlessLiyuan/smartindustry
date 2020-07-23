package com.smartindustry.outbound.service;

import com.smartindustry.common.vo.ResultVO;
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
}
