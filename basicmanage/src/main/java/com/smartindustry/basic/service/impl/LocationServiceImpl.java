package com.smartindustry.basic.service.impl;

import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ILocationService;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 货位管理
 * @version: 1.0
 */
@Service
public class LocationServiceImpl implements ILocationService {
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        return null;
    }

    @Override
    public ResultVO edit(LocationDTO dto) {
        return null;
    }

    @Override
    public ResultVO delete(List<Long> lids) {
        return null;
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        return null;
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        return null;
    }
}
