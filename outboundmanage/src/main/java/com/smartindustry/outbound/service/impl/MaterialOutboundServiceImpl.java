package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.sm.StorageBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ModuleConstant;
import com.smartindustry.common.mapper.om.LogisticsPictureMapper;
import com.smartindustry.common.mapper.om.LogisticsRecordMapper;
import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.pojo.om.LogisticsPicturePO;
import com.smartindustry.common.pojo.om.LogisticsRecordPO;
import com.smartindustry.common.util.FileUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.LogisticsDTO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import com.smartindustry.outbound.vo.OutboundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private LogisticsRecordMapper logisticsRecordMapper;
    @Autowired
    private LogisticsPictureMapper logisticsPictureMapper;
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<OutboundBO> page = PageHelper.startPage(pageNum, pageSize);
        List<OutboundBO> bos = outboundMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundVO.convert(bos)));
    }

    @Override
    public ResultVO upload(MultipartFile file) {
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalPath(), filePathConfig.getProjectDir() + filePathConfig.getOutboundDir() + filePathConfig.getLogisticsDir(), OutboundConstant.FILE_LOGISTICS);
        return new ResultVO<>(1000, filePathConfig.getPublicPath() + picture);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO logInsert(LogisticsDTO dto) {
        LogisticsRecordPO recordPO = LogisticsDTO.createPO(dto);
        logisticsRecordMapper.insert(recordPO);

        List<LogisticsPicturePO> picturePOs = new ArrayList<>();
        for (String pic : dto.getPicture()) {
            LogisticsPicturePO po = new LogisticsPicturePO();
            po.setLogisticsRecordId(recordPO.getLogisticsRecordId());
            po.setPicture(pic.split(filePathConfig.getPublicPath())[1]);
            picturePOs.add(po);
        }
        logisticsPictureMapper.batchInsert(picturePOs);

        return ResultVO.ok();
    }
}
