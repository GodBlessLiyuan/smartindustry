package com.smartindustry.outbound.util;

import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.mapper.om.PickBodyMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.om.LabelRecommendPO;
import com.smartindustry.common.pojo.om.PickBodyPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.outbound.constant.OutboundConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.*;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:23 2020/9/30
 * @version: 1.0.0
 * @description: 拣货订单推荐PID(业务工具类)
 */
public class PickRecommendUtil {
     @Autowired
     private PickBodyMapper pickBodyMapper;
     @Autowired
     private StorageLabelMapper storageLabelMapper;
    /**
     * 对拣货单列表进行推荐
     * @param pos  拣货单列表
     * @param flag  是否强关联(若强关联，则拣货单和库内表来源单号必须相同)
     */
    public void recommend(List<PickHeadPO> pos,boolean flag){
        Map<Long, LabelRecommendPO> labelRecommendPOs = new HashMap<>();

        for (PickHeadPO po : pos) {
            List<PickBodyBO> bodyBOs = pickBodyMapper.queryByHeadId(po.getPickHeadId());
            for (PickBodyBO bo : bodyBOs) {
                List<StorageLabelBO> storageLabelBOS = new ArrayList<>();
                if(flag){
                    storageLabelBOS = storageLabelMapper.queryNotRecommend(po.getSourceNo(), bo.getMaterialId());
                }else {
                    storageLabelBOS = storageLabelMapper.queryNotRecommend(null,bo.getMaterialId());
                }
            }
        }

//        for (PickBodyPO bodyPO : bodyPOs) {
//            List<StorageLabelBO> storageLabelBOS = new ArrayList<>();
//            if (null != configPO && OutboundConstant.V_YES.equals(configPO.getConfigValue())) {
//                storageLabelBOS = storageLabelMapper.queryNotRecommend(headPO.getSourceNo(), bodyPO.getMaterialId());
//            }else{
//                storageLabelBOS = storageLabelMapper.queryNotRecommend(null,bodyPO.getMaterialId());
//            }
//            Collections.sort(storageLabelBOS, new Comparator<StorageLabelBO>(){
//                @Override
//                public int compare(StorageLabelBO bo1, StorageLabelBO bo2) {
//                    int result = 0;
//                    try {
//                        result = DateUtil.YMD.parse(bo1.getProduceDate()).compareTo(DateUtil.YMD.parse(bo2.getProduceDate()));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    return result;
//                }
//            });
//            int num = 0;
//            for (StorageLabelBO storageLabelBO : storageLabelBOS) {
//                if (labelRecommendPOs.containsKey(storageLabelBO.getStorageLabelId())) {
//                    continue;
//                }
//
//                LabelRecommendPO labelRecommendPO = new LabelRecommendPO();
//                labelRecommendPO.setPickBodyId(bodyPO.getPickBodyId());
//                labelRecommendPO.setStorageLabelId(storageLabelBO.getStorageLabelId());
//                labelRecommendPOs.put(storageLabelBO.getStorageLabelId(), labelRecommendPO);
//
//                num += storageLabelBO.getStorageNum();
//                if (num >= bodyPO.getDemandNum()) {
////                        materialInventoryMap.put(storageLabelBO.getMaterialId(), num);
//                    break;
//                }
//            }
//
//            if (num < bodyPO.getDemandNum()) {
//                return;
//            }
//        }
//
//        labelRecommendMapper.batchInsert(new ArrayList<>(labelRecommendPOs.values()));
//        storageLabelMapper.updateStatus(new ArrayList<>(labelRecommendPOs.keySet()), OutboundConstant.WORK_ORDER_OUTBOUND);
//
//        headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_UNPROCESSED);
//        pickHeadMapper.updateByPrimaryKey(headPO);
    }
}
