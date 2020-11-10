package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.om.OutboundRecordBO;
import com.smartindustry.common.constant.ExceptionEnums;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.om.OutboundRecordMapper;
import com.smartindustry.common.pojo.om.OutboundRecordPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.dto.OperateDTO;
import com.smartindustry.outbound.service.IProduceOutboundService;
import com.smartindustry.outbound.vo.OutboundHeadVO;
import com.smartindustry.outbound.vo.OutboundRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:27 2020/11/8
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class ProduceOutboundServiceImpl implements IProduceOutboundService {
    @Autowired
    private OutboundHeadMapper outboundHeadMapper;
    @Autowired
    private OutboundRecordMapper outboundRecordMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<OutboundHeadBO> page = PageQueryUtil.startPage(reqData);
        List<OutboundHeadBO> bos = outboundHeadMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundHeadVO.convertBO(bos)));
    }

    @Override
    public ResultVO queryOutboundRecord(OperateDTO dto){
        List<OutboundRecordBO> bos = outboundRecordMapper.queryForkByOhid(dto.getOhid());
        return ResultVO.ok().setData(OutboundRecordVO.convert(bos));
    }

    @Override
    public ResultVO detail(OperateDTO dto){
        OutboundHeadBO bo = outboundHeadMapper.queryDetail(dto.getOhid());
        if(null == bo){
            // 出库单不存在
            return new ResultVO(ExceptionEnums.NO_EXIST.getCode());
        }
        return ResultVO.ok().setData(OutboundHeadVO.convert(bo));
    }
}
