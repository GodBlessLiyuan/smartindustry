package com.smartindustry.authority.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.authority.dto.DeptDTO;
import com.smartindustry.authority.service.IDeptService;
import com.smartindustry.authority.vo.DeptVO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:24 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public ResultVO deptQuery(Map<String, Object> reqData){
        Page<DeptPO> page = PageQueryUtil.startPage(reqData);
        List<DeptPO> pos = deptMapper.deptPageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), DeptVO.convert(pos)));
    }

}
