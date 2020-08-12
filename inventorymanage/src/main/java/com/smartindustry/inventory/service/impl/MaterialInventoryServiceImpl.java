package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialInventoryService;
import com.smartindustry.inventory.vo.MaterialInventoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialInventoryBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialInventoryBO> bos = materialInventoryMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialInventoryVO.convert(bos)));
    }
}
