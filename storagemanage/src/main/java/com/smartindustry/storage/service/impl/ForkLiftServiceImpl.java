package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
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
        //fff
        return ResultVO.ok() ;
    }
}
