package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ISalesOutboundService;
import com.smartindustry.basic.vo.SalesOutboundVO;
import com.smartindustry.common.bo.ds.SalesOutboundBO;
import com.smartindustry.common.mapper.ds.SalesOutboundMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@Service
@EnableTransactionManagement
public class SalesOutboundServiceImpl implements ISalesOutboundService {

    @Autowired
    private SalesOutboundMapper salesOutboundMapper;


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<SalesOutboundBO> bos = salesOutboundMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SalesOutboundVO.convert(bos)));
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        if (dto.getSoid() == null) {
            return  new ResultVO(1002);
        }
        SalesOutboundBO bo = salesOutboundMapper.queryBySoid(dto.getSoid());
        return ResultVO.ok().setData(SalesOutboundVO.convert(bo));
    }
}
