package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.om.LogisticsRecordBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.om.*;
import com.smartindustry.common.util.FileUtil;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import com.smartindustry.outbound.vo.LogisticsRecordVO;
import com.smartindustry.outbound.vo.OutboundDetailVO;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import com.smartindustry.outbound.vo.OutboundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:04
 * @description: 物料出库
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialOutboundServiceImpl implements IMaterialOutboundService {
    @Autowired
    private OutboundMapper outboundMapper;
    @Autowired
    private PickHeadMapper pickHeadMapper;
    @Autowired
    private PickBodyMapper pickBodyMapper;
    @Autowired
    private LogisticsRecordMapper logisticsRecordMapper;
    @Autowired
    private LogisticsPictureMapper logisticsPictureMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    private PickLabelMapper pickLabelMapper;
    @Autowired
    private LabelRecommendMapper labelRecommendMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<OutboundBO> page = PageQueryUtil.startPage(reqData);
        List<OutboundBO> bos = outboundMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundVO.convert(bos)));
    }

    @Override
    public ResultVO detail(Long oId) {
        OutboundBO outboundBO = outboundMapper.queryByOid(oId);
        if (null == outboundBO) {
            return new ResultVO(1002);
        }

        List<PrintLabelBO> labelBOs = pickLabelMapper.queryByPhid(outboundBO.getPickHeadId());

        return ResultVO.ok().setData(OutboundDetailVO.convert(outboundBO, labelBOs));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO outbound(Long oId) {
        OutboundPO outboundPO = outboundMapper.selectByPrimaryKey(oId);
        if (null == outboundPO) {
            return new ResultVO(1002);
        }
        PickHeadPO headPO = pickHeadMapper.selectByPrimaryKey(outboundPO.getPickHeadId());
        if (null == headPO) {
            return new ResultVO(1002);
        }

        Byte ostatus = OutboundConstant.PICK_OUTBOUND_ALL;
        List<PickBodyBO> bos = pickBodyMapper.queryByHeadId(headPO.getPickHeadId());
        Map<Long, Integer> materialInventoryMap = new HashMap<>();
        for (PickBodyBO bo : bos) {
            materialInventoryMap.put(bo.getMaterialId(), bo.getPickNum());
            if (!bo.getDemandNum().equals(bo.getPickNum())) {
                ostatus = OutboundConstant.PICK_OUTBOUND_LACK;
                break;
            }
        }

        outboundPO.setOutboundTime(new Date());
        outboundPO.setStatus(OutboundConstant.OUTBOUND_STATUS_FINISH);
        headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_FINISH);
        headPO.setOutboundStatus(ostatus);
        headPO.setOutboundTime(new Date());

        LogisticsRecordPO logisticsRecordPO = logisticsRecordMapper.queryByOid(oId);
        if (null != logisticsRecordPO) {
            outboundPO.setShipTime(new Date());
            headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_CONFIRM);
        }

        pickHeadMapper.updateByPrimaryKey(headPO);
        outboundMapper.updateByPrimaryKey(outboundPO);

        // 删除对应标签
        List<PrintLabelBO> printLabelBOs = pickLabelMapper.queryByPhid(headPO.getPickHeadId());
        List<Long> plIds = new ArrayList<>();
        for (PrintLabelBO printLabelBO : printLabelBOs) {
            plIds.add(printLabelBO.getPrintLabelId());
        }
        if (printLabelBOs.size() != 0) {
            storageLabelMapper.deleteByPlids(plIds);

            // 物料库存信息
            List<MaterialInventoryBO> materialInventoryBOs = materialInventoryMapper.queryByMids(new ArrayList<>(materialInventoryMap.keySet()));
            MaterialInventoryPO updateInventoryPO = new MaterialInventoryPO();
            for (MaterialInventoryBO materialInventoryBO : materialInventoryBOs) {
                updateInventoryPO.setStorageNum(-materialInventoryMap.get(materialInventoryBO.getMaterialId()));
                materialInventoryMapper.updateByPrimaryKey(materialInventoryBO.updatePO(updateInventoryPO));
            }
        }

        outboundRecordMapper.insert(new OutboundRecordPO(headPO.getPickHeadId(), outboundPO.getOutboundId(), 1L, "夏慧", OutboundConstant.RECORD_CONFIRM_OUTBOUND, OutboundConstant.MATERIAL_STATUS_FINISH));

        // 重新推荐货位
        new Thread(() -> {
            List<PickHeadPO> notRecommendHeadPOs = pickHeadMapper.queryNotRecommodByOno(headPO.getOrderNo());
            for (PickHeadPO notRecommendHeadPO : notRecommendHeadPOs) {
                List<PickBodyBO> bodyBOs = pickBodyMapper.queryByHeadId(notRecommendHeadPO.getPickHeadId());
                Map<Long, LabelRecommendPO> labelRecommendPOs = new HashMap<>();
                for (PickBodyBO bodyBO : bodyBOs) {
                    List<StorageLabelBO> storageLabelBOS = storageLabelMapper.queryNotRecommend(notRecommendHeadPO.getOrderNo(), bodyBO.getMaterialId());
                    int num = 0;
                    for (StorageLabelBO storageLabelBO : storageLabelBOS) {
                        if (labelRecommendPOs.containsKey(storageLabelBO.getStorageLabelId())) {
                            continue;
                        }

                        LabelRecommendPO labelRecommendPO = new LabelRecommendPO();
                        labelRecommendPO.setPickBodyId(bodyBO.getPickBodyId());
                        labelRecommendPO.setStorageLabelId(storageLabelBO.getStorageLabelId());
                        labelRecommendPOs.put(storageLabelBO.getStorageLabelId(), labelRecommendPO);

                        num += storageLabelBO.getStorageNum();
                        if (num >= bodyBO.getDemandNum()) {
                            break;
                        }
                    }

                    if (num < bodyBO.getDemandNum()) {
                        return;
                    }
                }

                labelRecommendMapper.batchInsert(new ArrayList<>(labelRecommendPOs.values()));
                storageLabelMapper.updateStatus(new ArrayList<>(labelRecommendPOs.keySet()), OutboundConstant.WORK_ORDER_OUTBOUND);

                notRecommendHeadPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_UNPROCESSED);
                pickHeadMapper.updateByPrimaryKey(notRecommendHeadPO);
            }
        }).start();

        return ResultVO.ok();
    }

    @Override
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalPath(), filePathConfig.getProjectDir() + filePathConfig.getOutboundDir() + filePathConfig.getLogisticsDir(), OutboundConstant.FILE_LOGISTICS);
        return ResultVO.ok().setData(filePathConfig.getPublicPath() + picture);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO logEdit(LogisticsRecordDTO dto) {
        OutboundPO outboundPO = outboundMapper.selectByPrimaryKey(dto.getOid());
        if (null == outboundPO) {
            return new ResultVO(1002);
        }
        PickHeadPO pickHeadPO = pickHeadMapper.selectByPrimaryKey(outboundPO.getPickHeadId());
        if (null == pickHeadPO) {
            return new ResultVO(1002);
        }

        if (null == dto.getLid()) {
            // 新增
            LogisticsRecordPO recordPO = LogisticsRecordDTO.createPO(dto);
            logisticsRecordMapper.insert(recordPO);

            if (null != dto.getPicture() && dto.getPicture().size() > 0) {
                dto.setLid(recordPO.getLogisticsRecordId());
                logisticsPictureMapper.batchInsert(LogisticsRecordDTO.createPicPO(dto, filePathConfig));
            }

            if (OutboundConstant.OUTBOUND_STATUS_FINISH.equals(outboundPO.getStatus())) {
                // 确认出货
                pickHeadPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_CONFIRM);
                pickHeadMapper.updateByPrimaryKey(pickHeadPO);
            }

            return ResultVO.ok();
        }

        // 编辑保存
        LogisticsRecordPO recordPO = logisticsRecordMapper.selectByPrimaryKey(dto.getLid());
        if (null == recordPO) {
            return new ResultVO(1002);
        }

        logisticsRecordMapper.updateByPrimaryKey(LogisticsRecordDTO.updatePO(recordPO, dto));

        logisticsPictureMapper.deleteBylid(dto.getLid());
        if (null != dto.getPicture() && dto.getPicture().size() > 0) {
            logisticsPictureMapper.batchInsert(LogisticsRecordDTO.createPicPO(dto, filePathConfig));
        }
        return ResultVO.ok();
    }

    @Override
    public ResultVO record(Long oId) {
        Map<String, Object> res = new HashMap<>();
        LogisticsRecordBO logisticsRecordBO = logisticsRecordMapper.queryByOid(oId);
        res.put(ResultConstant.LOGISTICS_RECORD, LogisticsRecordVO.convert(null == logisticsRecordBO ? new LogisticsRecordBO() : logisticsRecordBO, filePathConfig));
        List<OutboundRecordPO> outboundRecordPOs = outboundRecordMapper.queryByOid(oId);
        res.put(ResultConstant.OPERATE_RECORD, OutboundRecordVO.convert(outboundRecordPOs));
        return ResultVO.ok().setData(res);
    }
}
