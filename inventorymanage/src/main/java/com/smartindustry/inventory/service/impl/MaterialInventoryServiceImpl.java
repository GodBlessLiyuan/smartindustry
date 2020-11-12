package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.si.MaterialInventoryBO;
import com.smartindustry.common.bo.si.ProductDetailBO;
import com.smartindustry.common.constant.ExceptionEnums;
import com.smartindustry.common.mapper.si.MaterialInventoryMapper;
import com.smartindustry.common.pojo.si.MaterialInventoryPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.SafeStockDTO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import com.smartindustry.inventory.vo.MaterialInventoryVO;
import com.smartindustry.inventory.vo.ProductDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.*;

/**
 * @author: jiangchaojie
 * @date: Created in 2020/11/11
 * @description: 物料库存统计
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialInventoryServiceImpl implements IMaterialInventoryService {
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialInventoryBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialInventoryBO> bos = materialInventoryMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialInventoryVO.convert(bos)));
    }

    @Override
    public ResultVO safeStock(SafeStockDTO dto){
        List<MaterialInventoryPO> pos = SafeStockDTO.createPOS(dto);
        if(pos.isEmpty()){
            return new ResultVO(ExceptionEnums.NOT_EMPTY.getCode());
        }
        // 批量更新
        materialInventoryMapper.updateBatch(pos);
        return ResultVO.ok();
    }

    @Override
    public ResultVO pageQueryPro(Map<String, Object> reqData){
        Page<ProductDetailBO> page = PageQueryUtil.startPage(reqData);
        List<ProductDetailBO> bos = materialInventoryMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ProductDetailVO.convert(bos)));
    }
}
