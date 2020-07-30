package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.LocationDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.ILocationService;
import com.smartindustry.basic.vo.LocationRecordVO;
import com.smartindustry.basic.vo.LocationVO;
import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.LocationRecordMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.LocationRecordPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private LocationRecordMapper locationRecordMapper;
    @Autowired
    private StorageLabelMapper storageLabelMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<LocationBO> page = PageQueryUtil.startPage(reqData);
        List<LocationBO> bos = locationMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), LocationVO.convert(bos)));
    }

    @Override
    public ResultVO edit(LocationDTO dto) {
        LocationPO existPO = locationMapper.queryByLno(dto.getLno());
        if (null != existPO && (null == dto.getLid() || !dto.getLid().equals(existPO.getLocationId()))) {
            return new ResultVO(1004);
        }

        if (null == dto.getLid()) {
            // 新增
            LocationPO locationPO = LocationDTO.createPO(dto);
            locationMapper.insert(locationPO);
            locationRecordMapper.insert(new LocationRecordPO(locationPO.getLocationId(), 1L, BasicConstant.RECORD_ADD));
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

        locationRecordMapper.insert(new LocationRecordPO(locationPO.getLocationId(), 1L, BasicConstant.RECORD_MODIFY));

        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> lids) {
        List<StorageLabelPO> storageLabelPOs = storageLabelMapper.queryByLids(lids);
        if (null != storageLabelPOs && storageLabelPOs.size() > 0) {
            return new ResultVO(1007);
        }

        locationMapper.batchDelete(lids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        LocationBO locationBO = locationMapper.queryById(dto.getLid());
        if (null == locationBO) {
            return new ResultVO(1001);
        }

        return ResultVO.ok().setData(LocationVO.convert(locationBO));
    }

    @Override
    public ResultVO record(OperateDTO dto) {
        List<LocationRecordPO> locationRecordPOs = locationRecordMapper.queryByLid(dto.getLid());
        return ResultVO.ok().setData(LocationRecordVO.convert(locationRecordPOs));
    }
}
