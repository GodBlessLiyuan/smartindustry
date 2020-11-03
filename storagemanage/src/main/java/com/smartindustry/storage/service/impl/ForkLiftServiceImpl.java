package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.ForkLiftDTO;
import com.smartindustry.storage.service.IForkLiftService;
import com.smartindustry.storage.vo.ForkliftVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:16 2020/10/29
 * @version: 1.0.0
 * @description:
 */
@Service
public class ForkLiftServiceImpl implements IForkLiftService {
    @Autowired
    private ForkliftMapper forkliftMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<ForkliftPO> page = PageQueryUtil.startPage(reqData);
        List<ForkliftPO> bos = forkliftMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ForkliftVO.convert(bos)));
    }

    @Override
    public ResultVO edit(ForkLiftDTO dto){
        if (null == dto.getFid()) {
            ForkliftPO po = ForkLiftDTO.createPO(dto);
            forkliftMapper.insert(po);
        }
        ForkliftPO po = forkliftMapper.selectByPrimaryKey(dto.getFid());
        if (null == po) {
            // 采购单表头不存在
            return new ResultVO(1002);
        }
        ForkliftPO forkliftPO = ForkLiftDTO.buildPO(po,dto);
        forkliftMapper.updateByPrimaryKeySelective(forkliftPO);
        return ResultVO.ok();
    }
}
