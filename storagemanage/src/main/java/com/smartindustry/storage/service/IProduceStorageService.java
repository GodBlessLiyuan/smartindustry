package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:00 2020/11/4
 * @version: 1.0.0
 * @description:
 */
public interface IProduceStorageService {
    /**
     * 生产入库管理的分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);
}
