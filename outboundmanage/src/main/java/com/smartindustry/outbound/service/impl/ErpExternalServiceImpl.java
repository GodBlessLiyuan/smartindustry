package com.smartindustry.outbound.service.impl;

import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.om.LabelRecommendPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.LabelRecordPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.PickDTO;
import com.smartindustry.outbound.service.IErpExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PickHeadPO headPO = PickDTO.convert(dto.getHead());
        pickHeadMapper.insert(headPO);

        List<PickBodyPO> bodyPOs = PickDTO.convert(headPO, dto.getBody());
        pickBodyMapper.batchInsert(bodyPOs);

        // 推荐货位
        new Thread(() -> {
            Map<Long, LabelRecommendPO> labelRecommendPOs = new HashMap<>();
            for (PickBodyPO bodyPO : bodyPOs) {
                List<StorageLabelPO> storageLabelPOs = storageLabelMapper.queryNotRecommend(headPO.getOrderNo(), bodyPO.getMaterialNo());
                int num = 0;
                for (StorageLabelPO storageLabelPO : storageLabelPOs) {
                    if (labelRecommendPOs.containsKey(storageLabelPO.getStorageLabelId())) {
                        continue;
                    }

                    LabelRecommendPO labelRecommendPO = new LabelRecommendPO();
                    labelRecommendPO.setPickBodyId(bodyPO.getPickBodyId());
                    labelRecommendPO.setStorageLabelId(storageLabelPO.getStorageLabelId());
                    labelRecommendPOs.put(storageLabelPO.getStorageLabelId(), labelRecommendPO);

                    num += storageLabelPO.getStorageNum();
                    if (num >= bodyPO.getDemandNum()) {
                        break;
                    }
                }

                if (num < bodyPO.getDemandNum()) {
                    return;
                }
            }

            labelRecommendMapper.batchInsert(new ArrayList<>(labelRecommendPOs.values()));

            headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_UNPROCESSED);
            pickHeadMapper.updateByPrimaryKey(headPO);
        }).start();

        return ResultVO.ok();
    }
}