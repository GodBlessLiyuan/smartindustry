package com.smartindustry.outbound.service.impl;

import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.mapper.si.ConfigMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.ConfigPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.PickDTO;
import com.smartindustry.outbound.service.IErpExternalService;
import com.smartindustry.outbound.util.BusinessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    PickLabelMapper pickLabelMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO pick(PickDTO dto) {

        //当销售，生产，采购强关联时，工单所扫码的PID来源必须是销售采购来源
        ConfigPO configPO = configMapper.queryByKey(OutboundConstant.K_PID_RELATE);
        boolean flag = (null != configPO && OutboundConstant.V_YES.equals(configPO.getConfigValue()));
        UserPO user = tokenService.getLoginUser();
        PickHeadPO headPO = PickDTO.convert(dto.getHead());
        pickHeadMapper.insert(headPO);
        List<PickHeadPO> pos = new ArrayList<>();
        pos.add(headPO);


        List<PickBodyPO> bodyPOs = PickDTO.convert(headPO, dto.getBody());
        pickBodyMapper.batchInsert(bodyPOs);
        outboundRecordMapper.insert(new OutboundRecordPO(headPO.getPickHeadId(), null, user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_PICK));

       // 先推荐
        BusinessUtil businessUtil = new BusinessUtil();
        businessUtil.recommend(pos,flag,pickBodyMapper,pickHeadMapper,storageLabelMapper,labelRecommendMapper);

        // 然后更新推荐的库内标签pid
        List<Long> plIds = labelRecommendMapper.queryRecommedPlids(headPO.getPickHeadId());
        // 当出库时会根据不同的pid进行更新库内标签表
        switch (headPO.getSourceType()){
            case OutboundConstant.TYPE_OUT_WORK:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_PICK,storageLabelMapper);
                break;
            case OutboundConstant.TYPE_OUT_SHIP:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_SHIP,storageLabelMapper);
                break;
            case OutboundConstant.TYPE_OUT_OTHER:
                businessUtil.updateStorageLabel(plIds,OutboundConstant.TYPE_STORAGE_LABEL_TRANSFER,storageLabelMapper);
                break;
            default:
                break;
        }
        return ResultVO.ok();
    }
}