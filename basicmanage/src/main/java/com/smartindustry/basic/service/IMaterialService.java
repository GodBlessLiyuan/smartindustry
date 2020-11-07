package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:18
 * @description: 物料管理
 * @version: 1.0
 */
public interface IMaterialService {
    ResultVO pageQuery(Map<String, Object> reqData);

    ResultVO detail(OperateDTO dto);

}
