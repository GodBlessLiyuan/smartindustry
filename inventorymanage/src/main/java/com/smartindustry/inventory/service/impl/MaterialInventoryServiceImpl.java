package com.smartindustry.inventory.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:06
 * @description: 物料库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialInventoryServiceImpl implements IMaterialInventoryService {
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        return null;
    }
}
