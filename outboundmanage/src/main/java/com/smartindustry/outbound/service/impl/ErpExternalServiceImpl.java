package com.smartindustry.outbound.service.impl;

import com.netflix.loadbalancer.InterruptTask;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.om.LabelRecommendMapper;
import com.smartindustry.common.mapper.om.OutboundRecordMapper;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.om.LabelRecommendPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.ServletUtil;
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
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    TokenService tokenService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO pick(PickDTO dto) {
        UserPO user = tokenService.getLoginUser();
        PickHeadPO headPO = PickDTO.convert(dto.getHead());
        pickHeadMapper.insert(headPO);

        List<PickBodyPO> bodyPOs = PickDTO.convert(headPO, dto.getBody());
        pickBodyMapper.batchInsert(bodyPOs);
        outboundRecordMapper.insert(new OutboundRecordPO(headPO.getPickHeadId(), null, user.getUserId(), user.getName(), OutboundConstant.RECORD_ADD, OutboundConstant.MATERIAL_STATUS_PICK));

        // 推荐货位
        new Thread(() -> {
            Map<Long, LabelRecommendPO> labelRecommendPOs = new HashMap<>();
//            Map<Long, Integer> materialInventoryMap = new HashMap<>();
            for (PickBodyPO bodyPO : bodyPOs) {
                List<StorageLabelBO> storageLabelBOS = storageLabelMapper.queryNotRecommend(headPO.getOrderNo(), bodyPO.getMaterialId());
                int num = 0;
                for (StorageLabelBO storageLabelBO : storageLabelBOS) {
                    if (labelRecommendPOs.containsKey(storageLabelBO.getStorageLabelId())) {
                        continue;
                    }

                    LabelRecommendPO labelRecommendPO = new LabelRecommendPO();
                    labelRecommendPO.setPickBodyId(bodyPO.getPickBodyId());
                    labelRecommendPO.setStorageLabelId(storageLabelBO.getStorageLabelId());
                    labelRecommendPOs.put(storageLabelBO.getStorageLabelId(), labelRecommendPO);

                    num += storageLabelBO.getStorageNum();
                    if (num >= bodyPO.getDemandNum()) {
//                        materialInventoryMap.put(storageLabelBO.getMaterialId(), num);
                        break;
                    }
                }

                if (num < bodyPO.getDemandNum()) {
                    return;
                }
            }

            labelRecommendMapper.batchInsert(new ArrayList<>(labelRecommendPOs.values()));
            storageLabelMapper.updateStatus(new ArrayList<>(labelRecommendPOs.keySet()), OutboundConstant.WORK_ORDER_OUTBOUND);

            headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_UNPROCESSED);
            pickHeadMapper.updateByPrimaryKey(headPO);

//            // 物料库存信息
//            List<MaterialInventoryBO> materialInventoryBOs = materialInventoryMapper.queryByMids(new ArrayList<>(materialInventoryMap.keySet()));
//            MaterialInventoryPO updateInventoryPO = new MaterialInventoryPO();
//            for (MaterialInventoryBO materialInventoryBO : materialInventoryBOs) {
//                updateInventoryPO.setStorageNum(-materialInventoryMap.get(materialInventoryBO.getMaterialId()));
//                materialInventoryMapper.updateByPrimaryKey(materialInventoryBO.updatePO(updateInventoryPO));
//            }
        }).start();

        return ResultVO.ok();
    }
}