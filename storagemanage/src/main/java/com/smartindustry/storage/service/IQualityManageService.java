package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.dto.QeConfirmDTO;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:39
 * @description: 质量管理
 * @version: 1.0
 */
public interface IQualityManageService {

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData);

    /**
     * IQC检验
     *
     * @param dto
     * @return
     */
    ResultVO iqcTest(IqcTestDTO dto);

    /**
     * QE确认
     *
     * @param dto
     * @return
     */
    ResultVO qeConfirm(QeConfirmDTO dto);

    /**
     * 生产入库
     *
     * @param rbId
     * @return
     */
    ResultVO storage(Long rbId);

    /**
     * 查询记录
     *
     * @param rbId
     * @param status
     * @return
     */
    ResultVO record(Long rbId, Byte status);
}
