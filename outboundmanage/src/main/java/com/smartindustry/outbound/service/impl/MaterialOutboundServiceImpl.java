package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.om.LogisticsRecordBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.om.PickBodyBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.em.TransferHeadMapper;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.mapper.si.ConfigMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.mapper.sm.StorageMapper;
import com.smartindustry.common.mapper.sm.StorageRecordMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.em.TransferHeadPO;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.om.*;
import com.smartindustry.common.pojo.si.ConfigPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
import com.smartindustry.common.pojo.sm.StoragePO;
import com.smartindustry.common.pojo.sm.StorageRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.FileUtil;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.LogisticsRecordDTO;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import com.smartindustry.outbound.util.OmNoUtil;
import com.smartindustry.outbound.util.BusinessUtil;
import com.smartindustry.outbound.vo.LogisticsRecordVO;
import com.smartindustry.outbound.vo.OutboundDetailVO;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import com.smartindustry.outbound.vo.OutboundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private StorageRecordMapper storageRecordMapper;
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    TokenService tokenService;
    @Autowired
    StorageMapper storageMapper;
    @Autowired
    LocationMapper locationMapper;
    @Autowired
    TransferHeadMapper transferHeadMapper;
    @Autowired
    ConfigMapper configMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<OutboundBO> page = PageQueryUtil.startPage(reqData);
        List<OutboundBO> bos = outboundMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundVO.convert(bos)));
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        OutboundBO outboundBO = outboundMapper.queryByOid(dto.getOid());
        if (null == outboundBO) {
            return new ResultVO(1002);
        }

        List<PrintLabelBO> labelBOs = pickLabelMapper.queryByPhid(outboundBO.getPickHeadId());

        return ResultVO.ok().setData(OutboundDetailVO.convert(outboundBO, labelBOs));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO outbound(OperateDTO dto) {
        UserPO user = tokenService.getLoginUser();
        OutboundPO outboundPO = outboundMapper.selectByPrimaryKey(dto.getOid());
        if (null == outboundPO) {
            return new ResultVO(1002);
        }
        PickHeadPO headPO = pickHeadMapper.selectByPrimaryKey(outboundPO.getPickHeadId());
        List<PrintLabelPO> pos1 = pickHeadMapper.queryByPhidItems(headPO.getPickHeadId());
        if (null == headPO) {
            return new ResultVO(1002);
        }

        // 对应PID的物料是否被锁定
        List<PrintLabelBO> lockBOs = pickLabelMapper.queryLockByPhid(headPO.getPickHeadId());
        if(null != lockBOs && lockBOs.size() > 0) {
            return new ResultVO(1004);
        }

        Byte ostatus = OutboundConstant.PICK_OUTBOUND_ALL;
        List<PickBodyBO> bos = pickBodyMapper.queryByHeadId(headPO.getPickHeadId());
        Map<Long, Integer> materialInventoryMap = new HashMap<>();
        for (PickBodyBO bo : bos) {
            materialInventoryMap.put(bo.getMaterialId(), bo.getPickNum());
            if (bo.getDemandNum() > bo.getPickNum()) {
                ostatus = OutboundConstant.PICK_OUTBOUND_LACK;
                break;
            }
        }
        Date date = new Date();
        outboundPO.setOutboundTime(date);
        outboundPO.setStatus(OutboundConstant.OUTBOUND_STATUS_FINISH);
        headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_FINISH);
        headPO.setOutboundStatus(ostatus);
        headPO.setOutboundTime(date);

        LogisticsRecordPO logisticsRecordPO = logisticsRecordMapper.queryByOid(dto.getOid());
        if (null != logisticsRecordPO) {
            outboundPO.setShipTime(date);
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
        // 清除掉所有得标签推荐表得内容
        labelRecommendMapper.deleteAll();

        outboundRecordMapper.insert(new OutboundRecordPO(headPO.getPickHeadId(), outboundPO.getOutboundId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_CONFIRM_OUTBOUND, OutboundConstant.MATERIAL_STATUS_FINISH));
        //当销售，生产，采购强关联时，工单所扫码的PID来源必须是销售采购来源
        ConfigPO configPO = configMapper.queryByKey(OutboundConstant.K_PID_RELATE);
        boolean flag = (null != configPO && OutboundConstant.V_YES.equals(configPO.getConfigValue()));
        // 当出库时，重新刷新所有的推荐列表
        List<PickHeadPO> notRecommendHeadPOs;
        if (flag) {
            notRecommendHeadPOs = pickHeadMapper.queryNotRecommodByOno(headPO.getSourceNo());
        }else {
            notRecommendHeadPOs = pickHeadMapper.queryNotRecommodByOno(null);
        }
        BusinessUtil businessUtil = new BusinessUtil();
        businessUtil.recommend(notRecommendHeadPOs,flag,pickBodyMapper,pickHeadMapper,storageLabelMapper,labelRecommendMapper);

        //当出库是调拨出库时,会生成新得入库单
        OutboundBO po = outboundMapper.queryByOid(dto.getOid());
        TransferHeadPO po2 = transferHeadMapper.queryNo(headPO.getSourceNo());
        if (po.getSourceType().equals(OutboundConstant.TYPE_TRANSFER) && null!=po2) {
            StoragePO po1 = new StoragePO();
            String head = OmNoUtil.MATERIAL_STORAGE_QTCK;
            po1.setStorageNo(OmNoUtil.genStorageNo(storageMapper, head, new Date()));
            po1.setSourceNo(po.getOutboundNo());
            po1.setSourceType(OutboundConstant.TYPE_TRANSFER_INSERT);
            Integer sum = pickBodyMapper.queryPickNum(po.getPickHeadId());
            List<LocationPO> pos = locationMapper.queryLocation(po.getStorageWid());
            if (pos.isEmpty()) {
                po1.setStoredNum(sum);
                po1.setStatus(OutboundConstant.MATERIAL_STORAGE_BEING);
            } else {
                po1.setStoredNum(0);
                po1.setStatus(OutboundConstant.MATERIAL_STORAGE_PENDING);
            }
            po1.setPendingNum(sum);
            po1.setCreateTime(new Date());
            po1.setDr((byte) 1);
            po1.setWarehouseId(po2.getStorageWid());
            storageMapper.insert(po1);
            storageRecordMapper.insert(new StorageRecordPO(dto.getSid(), po1.getStorageId(), user.getUserId(), user.getName(), OutboundConstant.RECORD_TYPE_STORAGE_INVOICE, OutboundConstant.RECEIPT_MATERIAL_STORAGE));
        }
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
                // 更新出库单的出货时间
                outboundPO.setShipTime(new Date());
                outboundMapper.updateByPrimaryKeySelective(outboundPO);
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
    public ResultVO record(OperateDTO dto) {
        Map<String, Object> res = new HashMap<>();
        LogisticsRecordBO logisticsRecordBO = logisticsRecordMapper.queryByOid(dto.getOid());
        res.put(ResultConstant.LOGISTICS_RECORD, LogisticsRecordVO.convert(null == logisticsRecordBO ? new LogisticsRecordBO() : logisticsRecordBO, filePathConfig));
        List<OutboundRecordPO> outboundRecordPOs = outboundRecordMapper.queryByOid(dto.getOid());
        res.put(ResultConstant.OPERATE_RECORD, OutboundRecordVO.convert(outboundRecordPOs));
        return ResultVO.ok().setData(res);
    }
}
