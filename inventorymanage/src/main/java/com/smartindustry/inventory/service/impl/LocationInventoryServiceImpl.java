package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.ILocationInventoryService;
import com.smartindustry.inventory.vo.LocationInventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:06
 * @description: 货位库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class LocationInventoryServiceImpl implements ILocationInventoryService {
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
//        Page<LocationBO> page = PageQueryUtil.startPage(reqData);
//        List<LocationBO> bos = locationMapper.pageQuery(reqData);
//
//        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), LocationInventoryVO.convert(bos)));
        return null;
    }
}
