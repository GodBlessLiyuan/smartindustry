package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IProduceOutboundService;
import com.smartindustry.outbound.vo.OutboundHeadVO;
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

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<OutboundHeadBO> page = PageQueryUtil.startPage(reqData);
        List<OutboundHeadBO> bos = outboundHeadMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundHeadVO.convertBO(bos)));
    }
}
