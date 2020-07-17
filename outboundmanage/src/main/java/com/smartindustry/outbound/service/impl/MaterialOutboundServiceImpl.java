package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.LogisticsRecordBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import com.smartindustry.common.pojo.om.OutboundPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.util.FileUtil;
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private LogisticsRecordMapper logisticsRecordMapper;
    @Autowired
    private LogisticsPictureMapper logisticsPictureMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    private PickLabelMapper pickLabelMapper;
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<OutboundBO> page = PageHelper.startPage(pageNum, pageSize);
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

        outboundPO.setOutboundTime(new Date());
        outboundPO.setStatus(OutboundConstant.OUTBOUND_STATUS_FINISH);
        headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_FINISH);

        LogisticsRecordPO logisticsRecordPO = logisticsRecordMapper.queryByOid(oId);
        if (null != logisticsRecordPO) {
            outboundPO.setShipTime(new Date());
            headPO.setMaterialStatus(OutboundConstant.MATERIAL_STATUS_CONFIRM);
        }

        pickHeadMapper.updateByPrimaryKey(headPO);
        outboundMapper.updateByPrimaryKey(outboundPO);

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

            dto.setLid(recordPO.getLogisticsRecordId());
            logisticsPictureMapper.batchInsert(LogisticsRecordDTO.createPicPO(dto, filePathConfig));

            if (OutboundConstant.OUTBOUND_STATUS_WAIT.equals(outboundPO.getStatus())) {
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
        logisticsPictureMapper.batchInsert(LogisticsRecordDTO.createPicPO(dto, filePathConfig));

        return ResultVO.ok();
    }

    @Override
    public ResultVO record(Long oId) {
        Map<String, Object> res = new HashMap<>();
        LogisticsRecordBO logisticsRecordBO = logisticsRecordMapper.queryByOid(oId);
        if (null != logisticsRecordBO) {
            res.put("logistics", LogisticsRecordVO.convert(logisticsRecordBO, filePathConfig));
        }
        List<OutboundRecordPO> outboundRecordPOs = outboundRecordMapper.queryByOid(oId);
        res.put("operate", OutboundRecordVO.convert(outboundRecordPOs));
        return ResultVO.ok().setData(res);
    }
}
