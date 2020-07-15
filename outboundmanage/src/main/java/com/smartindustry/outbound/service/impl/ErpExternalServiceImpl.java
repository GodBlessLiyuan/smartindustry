package com.smartindustry.outbound.service.impl;

import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.PickDTO;
import com.smartindustry.outbound.service.IErpExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/15 15:11
 * @description: ERP 外部接口
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class ErpExternalServiceImpl implements IErpExternalService {
    @Autowired
    private PickHeadMapper pickHeadMapper;
    @Autowired
    private PickBodyMapper pickBodyMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private LabelRecommendMapper labelRecommendMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO pick(PickDTO dto) {
        return null;
    }
}