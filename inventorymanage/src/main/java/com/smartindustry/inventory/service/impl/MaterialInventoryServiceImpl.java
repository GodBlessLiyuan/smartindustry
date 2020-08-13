package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.im.SafeStockMapper;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.im.SafeStockPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.SafeStockDTO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import com.smartindustry.inventory.vo.MaterialInventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:06
 * @description: 物料库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialInventoryServiceImpl implements IMaterialInventoryService {
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;
    @Autowired
    private SafeStockMapper safeStockMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialInventoryBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialInventoryBO> bos = materialInventoryMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialInventoryVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO safeStock(SafeStockDTO dto) {
        if (null == dto.getSsid()) {
            // 新增
            SafeStockPO po = SafeStockDTO.createPO(dto);
            po.setUserId(1L);
            safeStockMapper.insert(po);
        } else {
            // 修改
            SafeStockPO po = safeStockMapper.selectByPrimaryKey(dto.getSsid());
            if (null == po) {
                return new ResultVO(1002);
            }

            safeStockMapper.updateByPrimaryKey(SafeStockDTO.buildPO(po, dto));
        }

        MaterialInventoryBO materialInventoryBO = materialInventoryMapper.queryByMid(dto.getMid());
        materialInventoryMapper.updateByPrimaryKey(materialInventoryBO.updatePO(new MaterialInventoryPO()));

        return ResultVO.ok();
    }
}
