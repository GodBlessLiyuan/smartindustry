package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.service.IMaterialDetailService;
import com.smartindustry.inventory.vo.StorageLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:05
 * @description: 物料库存明细查询
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialDetailServiceImpl implements IMaterialDetailService {
    @Autowired
    private StorageLabelMapper storageLabelMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<StorageLabelBO> page = PageQueryUtil.startPage(reqData);
        List<StorageLabelBO> bos = storageLabelMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StorageLabelVO.convert(bos)));
    }
}
