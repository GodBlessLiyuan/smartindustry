package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.WarehouseDTO;
import com.smartindustry.basic.service.IWarehouseService;
import com.smartindustry.basic.vo.WarehouseVO;
import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.WarehouseMapper;
import com.smartindustry.common.pojo.si.WarehousePO;
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
 * @date: Created in 2020/7/29 9:20
 * @description: 仓库管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class WarehouseServiceImpl implements IWarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private LocationMapper locationMapper;


    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<WarehouseBO> page = PageQueryUtil.startPage(reqData);
        List<WarehouseBO> bos = warehouseMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), WarehouseVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO edit(WarehouseDTO dto) {
        WarehousePO existPO = warehouseMapper.queryByNo(dto.getWno());
        if (null != existPO && !existPO.getWarehouseId().equals(dto.getWid())) {
            return new ResultVO(1004);
        }

        if (null == dto.getWid()) {
            // 新增
            WarehousePO warehousePO = WarehouseDTO.createPO(dto);
            warehouseMapper.insert(warehousePO);
            return ResultVO.ok();
        }
        // 编辑
        WarehousePO warehousePO = warehouseMapper.selectByPrimaryKey(dto.getWid());
        if (null == warehousePO) {
            return new ResultVO(1002);
        }

        WarehouseDTO.buildPO(warehousePO, dto);
        warehousePO.setUpdateTime(new Date());
        warehouseMapper.updateByPrimaryKey(warehousePO);
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> wids) {
        List<LocationBO> locationBOs = locationMapper.queryByWids(wids);
        if (null != locationBOs && locationBOs.size() > 0) {
            return new ResultVO(1007);
        }

        warehouseMapper.batchDelete(wids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        WarehouseBO warehouseBO = warehouseMapper.queryById(dto.getWid());
        if (null == warehouseBO) {
            return new ResultVO(1002);
        }

        return ResultVO.ok().setData(WarehouseVO.convert(warehouseBO));
    }


    @Override
    public ResultVO queryAll() {
        List<WarehouseBO> warehouseBOS = warehouseMapper.queryAll();
        return ResultVO.ok().setData(warehouseBOS);
    }
}
