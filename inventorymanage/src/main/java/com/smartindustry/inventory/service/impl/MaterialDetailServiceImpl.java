package com.smartindustry.inventory.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.bo.si.StorageLabelBO;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.si.StorageLabelMapper;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import com.smartindustry.common.pojo.si.StorageLabelPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.MaterialDetailDTO;
import com.smartindustry.inventory.service.IMaterialDetailService;
import com.smartindustry.inventory.vo.StorageLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    private MaterialInventoryMapper materialInventoryMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<StorageLabelBO> page = PageQueryUtil.startPage(reqData);
        List<StorageLabelBO> bos = storageLabelMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StorageLabelVO.convert(bos)));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO lock(MaterialDetailDTO dto) {
        List<StorageLabelPO> pos = storageLabelMapper.qureyBySlids(dto.getSlids());
        if (pos.size() != dto.getSlids().size()) {
            return new ResultVO(1002);
        }

        storageLabelMapper.updateMlid(dto.getSlids(), dto.getMlid());

        // 物料库存
        Map<Long, Integer> msMap = new HashMap<>();
        for (StorageLabelPO po : pos) {
            Long mid = po.getMaterialId();
            if (msMap.containsKey(mid)) {
                msMap.put(mid, msMap.get(mid) + po.getStorageNum());
            } else {
                msMap.put(mid, po.getStorageNum());
            }
        }

        List<MaterialInventoryBO> bos = materialInventoryMapper.queryByMids(new ArrayList<>(msMap.keySet()));
        MaterialInventoryPO updatePO = new MaterialInventoryPO();
        for (MaterialInventoryBO bo : bos) {
            updatePO.setLockNum(msMap.get(bo.getMaterialId()));
            materialInventoryMapper.updateByPrimaryKey(bo.updatePO(updatePO));
        }

        return ResultVO.ok();
    }
}
