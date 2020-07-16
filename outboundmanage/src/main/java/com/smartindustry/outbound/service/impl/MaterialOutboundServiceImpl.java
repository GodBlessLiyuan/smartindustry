package com.smartindustry.outbound.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.sm.StorageBO;
import com.smartindustry.common.mapper.om.OutboundMapper;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.service.IMaterialOutboundService;
import com.smartindustry.outbound.vo.OutboundVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/14 19:04
 * @description: 物料出库
 * @version: 1.0
 */
@Service
public class MaterialOutboundServiceImpl implements IMaterialOutboundService {
    @Autowired
    private OutboundMapper outboundMapper;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<OutboundBO> page = PageHelper.startPage(pageNum, pageSize);
        List<OutboundBO> bos = outboundMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), OutboundVO.convert(bos)));
    }
}
