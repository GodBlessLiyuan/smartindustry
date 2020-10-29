package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.om.MixHeadBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.om.MixHeadMapper;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.om.OutboundRecordMapper;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.StorageConstant;
import com.smartindustry.storage.constant.StorageExceptionEnums;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.OutboundHeadDTO;
import com.smartindustry.storage.service.IOutBoundService;
import com.smartindustry.storage.util.StorageNoUtil;
import com.smartindustry.storage.vo.MixHeadVO;
import com.smartindustry.storage.vo.OutboundHeadVO;
import com.smartindustry.storage.vo.OutboundRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:26 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class OutBoundServiceImpl implements IOutBoundService {
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundBodyMapper outboundBodyMapper;
    @Autowired
    private MixHeadMapper mixHeadMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<OutboundHeadPO> page = PageQueryUtil.startPage(reqData);
        List<OutboundHeadPO> bos = outboundHeadMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundHeadVO.convert(bos)));
    }

    @Override
    public ResultVO queryMix(){
        //只查询当天的混料工单
        List<MixHeadBO> bos = mixHeadMapper.queryMix();
        return ResultVO.ok().setData(MixHeadVO.convert(bos));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(OutboundHeadDTO dto){
        // 新增
        if (null == dto.getOhid()) {
            OutboundHeadPO po = OutboundHeadDTO.createPO(dto);
            po.setOutboundNo(StorageNoUtil.genOutboundHeadNo(outboundHeadMapper,StorageNoUtil.OUTBOUND_HEAD_YP,new Date()));
            if(dto.getFlag()){
                po.setStatus(StorageConstant.STATUS_OUTED);
                po.setOutboundTime(new Date());
            }else {
                po.setStatus(StorageConstant.STATUS_OUTING);
            }
            outboundHeadMapper.insert(po);
            if(!dto.getBody().isEmpty()){
                // 采购入库单表体
                List<OutboundBodyPO> pos = OutboundHeadDTO.convert(po,dto.getBody());
                outboundBodyMapper.batchInsert(pos);
            }
            if(dto.getFlag()){
                outboundRecordMapper.insert(new OutboundRecordPO(po.getOutboundHeadId(),1L,StorageConstant.OPERATE_NAME_AGREE_OUT));
            }else {
                outboundRecordMapper.insert(new OutboundRecordPO(po.getOutboundHeadId(),1L,StorageConstant.OPERATE_NAME_INSERT_OUT));
            }
            return ResultVO.ok();
        }
        OutboundHeadPO po = outboundHeadMapper.selectByPrimaryKey(dto.getOhid());
        if (null == po) {
            // 采购单表体不存在
            return new ResultVO(StorageExceptionEnums.NO_EXIST.getCode());
        }
        if(dto.getFlag()){
            po.setStatus(StorageConstant.STATUS_OUTED);
            po.setOutboundTime(new Date());
        }else {
            po.setStatus(StorageConstant.STATUS_OUTING);
        }
        OutboundHeadPO headPO = OutboundHeadDTO.buildPO(po,dto);
        outboundHeadMapper.updateByPrimaryKeySelective(headPO);
        if(!dto.getBody().isEmpty()){
            // 更新出库单表体，目前能改得就是出库数量和出库时间
            List<OutboundBodyPO> pos = OutboundHeadDTO.convert(po,dto.getBody());
            outboundBodyMapper.batchUpdate(pos);
        }
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto){
        OutboundHeadBO bo = outboundHeadMapper.queryByOhid(dto.getOhid());
        if(null == bo){
            // 出库单表体不存在
            return new ResultVO(StorageExceptionEnums.NO_EXIST.getCode());
        }
        return ResultVO.ok().setData(OutboundHeadVO.convert(bo));
    }

    @Override
    public ResultVO queryOutboundRecord(OperateDTO dto){
        List<OutboundRecordPO> pos = outboundRecordMapper.queryByOhid(dto.getOhid());
        return ResultVO.ok().setData(OutboundRecordVO.convert(pos));
    }
}
