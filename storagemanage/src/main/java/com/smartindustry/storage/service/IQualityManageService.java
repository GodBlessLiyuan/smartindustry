package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.IqcTestDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.QeConfirmDTO;
import com.smartindustry.storage.dto.QeTestDTO;
import org.springframework.web.bind.annotation.RequestBody;

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
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * IQC检验
     *
     * @param dto
     * @return
     */
    ResultVO iqcTest(IqcTestDTO dto);

    /**
     * QE 检验
     *
     * @param dto
     * @return
     */
    ResultVO qeTest(QeTestDTO dto);

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
     * @param dto
     * @return
     */
    ResultVO storage(@RequestBody OperateDTO dto);

    /**
     * 查询记录
     *
     * @param dto
     * @return
     */
    ResultVO record(@RequestBody OperateDTO dto);
}
