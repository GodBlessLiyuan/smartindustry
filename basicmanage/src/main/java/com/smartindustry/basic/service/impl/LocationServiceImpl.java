package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ILocationService;
import com.smartindustry.basic.vo.LocationRecordVO;
import com.smartindustry.basic.vo.LocationVO;
import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.si.LocationRecordBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.LocationRecordMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.LocationRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 货位管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class LocationServiceImpl implements ILocationService {
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private LocationRecordMapper locationRecordMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<LocationBO> page = PageQueryUtil.startPage(reqData);
        List<LocationBO> bos = locationMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), LocationVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(LocationDTO dto) {
        UserPO userPO = tokenService.getLoginUser();
        LocationPO existPO = locationMapper.queryByLno(dto.getLno());
        if (null != existPO && !existPO.getLocationId().equals(dto.getLid())) {
            return new ResultVO(1004);
        }

        if (null == dto.getLid()) {
            // 新增
            LocationPO locationPO = LocationDTO.createPO(dto);
            locationPO.setUserId(userPO.getUserId());
            locationMapper.insert(locationPO);
            locationRecordMapper.insert(new LocationRecordPO(locationPO.getLocationId(),userPO.getUserId(),"新增"));
            return ResultVO.ok();
        }
        // 编辑
        LocationPO locationPO = locationMapper.selectByPrimaryKey(dto.getLid());
        if (null == locationPO) {
            return new ResultVO(1002);
        }

        LocationDTO.buildPO(locationPO, dto);
        locationPO.setUpdateTime(new Date());
        locationMapper.updateByPrimaryKey(locationPO);
        locationRecordMapper.insert(new LocationRecordPO(locationPO.getLocationId(),userPO.getUserId(),"编辑"));
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> lids) {
        locationMapper.batchDelete(lids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        LocationBO locationBO = locationMapper.queryById(dto.getLid());
        if (null == locationBO) {
            return new ResultVO(1002);
        }

        return ResultVO.ok().setData(LocationVO.convert(locationBO));
    }


    @Override
    public ResultVO queryAll() {
        List<Map<String, Object>> res = locationMapper.queryAll();
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO queryByWid(OperateDTO dto) {
        List<Map<String, Object>> res = locationMapper.queryKvByWid(dto.getWid());
        return ResultVO.ok().setData(res);
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        List<LocationRecordBO> bos=locationRecordMapper.listByLocatonID(dto.getLid());
        return new ResultVO(1000, LocationRecordVO.convert(bos));
    }
}
