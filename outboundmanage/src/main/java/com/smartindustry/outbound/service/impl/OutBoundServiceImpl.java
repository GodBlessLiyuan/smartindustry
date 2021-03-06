package com.smartindustry.outbound.service.impl;


import com.github.pagehelper.Page;
import com.smartindustry.common.bo.om.MixHeadBO;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.om.OutboundRecordBO;
import com.smartindustry.common.constant.ExceptionEnums;
import com.smartindustry.common.mapper.om.*;
import com.smartindustry.common.mapper.wo.SlurryMaterialMapper;
import com.smartindustry.common.mapper.wo.SlurryOrderMapper;
import com.smartindustry.common.pojo.om.MixBodyPO;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.pojo.wo.SlurryMaterialPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.constant.OutboundConstant;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.dto.OutboundHeadDTO;
import com.smartindustry.outbound.service.IOutBoundService;
import com.smartindustry.outbound.util.OutboundNoUtil;
import com.smartindustry.outbound.vo.MixHeadVO;
import com.smartindustry.outbound.vo.OutboundHeadVO;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private SlurryOrderMapper slurryOrderMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;
    @Autowired
    private SlurryMaterialMapper slurryMaterialMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<OutboundHeadPO> page = PageQueryUtil.startPage(reqData);
        List<OutboundHeadPO> bos = outboundHeadMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundHeadVO.convert(bos)));
    }

    @Override
    public ResultVO queryMix(){
        //只查询当天的混料工单
        List<MixHeadBO> bos = slurryOrderMapper.queryMix();
        return ResultVO.ok().setData(MixHeadVO.convert(bos));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(OutboundHeadDTO dto){
        // 新增
        if (null == dto.getOhid()) {
            OutboundHeadPO po = OutboundHeadDTO.createPO(dto);
            po.setOutboundNo(OutboundNoUtil.genOutboundHeadNo(outboundHeadMapper,OutboundNoUtil.OUTBOUND_HEAD_YP,new Date()));
            if(dto.getFlag()){
                po.setOutboundNum(dto.getBody().stream().map(x -> x.getOnum()).reduce(BigDecimal.ZERO,BigDecimal::add));
                po.setStatus(OutboundConstant.STATUS_OUTED);
                po.setOutboundTime(new Date());
            }else {
                po.setStatus(OutboundConstant.STATUS_OUTING);
            }
            outboundHeadMapper.insert(po);
            if(!dto.getBody().isEmpty()){
                // 采购入库单表体
                List<OutboundBodyPO> pos = OutboundHeadDTO.convert(po,dto.getBody());
                outboundBodyMapper.batchInsert(pos);
            }
            if(dto.getFlag()){
                // 混料单将使用掉得物料减去，若当前使用得剩余小于出库数量，则提示物料不足
                for(OutboundHeadDTO.OutboundBodyDTO bodyDTO : dto.getBody()){
                    BigDecimal num = slurryOrderMapper.queryByMhid(dto.getMixno(),bodyDTO.getMid());
                    if(num.compareTo(bodyDTO.getOnum()) == -1){
                        return new ResultVO(ExceptionEnums.MATERIAL_MISS.getCode());
                    }else{
                        BigDecimal curNum = num.subtract(bodyDTO.getOnum());
                        SlurryMaterialPO mixBodyPO = new SlurryMaterialPO();
                        mixBodyPO.setPlanNum(curNum);
                        mixBodyPO.setSlurryMaterialId(bodyDTO.getMbid());
                        slurryMaterialMapper.updateByPrimaryKeySelective(mixBodyPO);
                    }
                }
                outboundRecordMapper.insert(new OutboundRecordPO(po.getOutboundHeadId(),1L,OutboundConstant.OPERATE_NAME_AGREE_OUT));
            }else {
                outboundRecordMapper.insert(new OutboundRecordPO(po.getOutboundHeadId(),1L,OutboundConstant.OPERATE_NAME_INSERT_OUT));
            }
            return ResultVO.ok();
        }
        OutboundHeadPO po = outboundHeadMapper.selectByPrimaryKey(dto.getOhid());
        if (null == po) {
            // 采购单表体不存在
            return new ResultVO(ExceptionEnums.NO_EXIST.getCode());
        }
        if(dto.getFlag()){
            // 混料单将使用掉得物料减去，若当前使用得剩余小于出库数量，则提示物料不足
            for(OutboundHeadDTO.OutboundBodyDTO bodyDTO : dto.getBody()){
                BigDecimal num = slurryOrderMapper.queryByMhid(dto.getMixno(),bodyDTO.getMid());
                if(num.compareTo(bodyDTO.getOnum()) == -1){
                    return new ResultVO(ExceptionEnums.MATERIAL_MISS.getCode());
                }else{
                    BigDecimal curNum = num.subtract(bodyDTO.getOnum());
                    SlurryMaterialPO mixBodyPO = new SlurryMaterialPO();
                    mixBodyPO.setPlanNum(curNum);
                    mixBodyPO.setSlurryMaterialId(bodyDTO.getMbid());
                    slurryMaterialMapper.updateByPrimaryKeySelective(mixBodyPO);
                }
            }
            po.setStatus(OutboundConstant.STATUS_OUTED);
            po.setOutboundTime(new Date());
            po.setOutboundNum(dto.getBody().stream().map(x -> x.getOnum()).reduce(BigDecimal.ZERO,BigDecimal::add));
        }else {
            po.setStatus(OutboundConstant.STATUS_OUTING);
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
        List<OutboundHeadBO> bo = outboundHeadMapper.queryMixByOhid(dto.getOhid());
        if(null == bo && !bo.isEmpty()){
            // 出库单表体不存在
            return new ResultVO(ExceptionEnums.NO_EXIST.getCode());
        }
        return ResultVO.ok().setData(OutboundHeadVO.convert(bo.get(0)));
    }

    @Override
    public ResultVO queryOutboundRecord(OperateDTO dto){
        List<OutboundRecordBO> pos = outboundRecordMapper.queryByOhid(dto.getOhid());
        return ResultVO.ok().setData(OutboundRecordVO.convert(pos));
    }
}
