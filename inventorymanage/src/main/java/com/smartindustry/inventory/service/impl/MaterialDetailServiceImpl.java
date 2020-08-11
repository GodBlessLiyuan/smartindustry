package com.smartindustry.inventory.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:05
 * @description: 物料库存明细查询
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialDetailServiceImpl implements IMaterialDetailService {
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        return null;
    }
}
