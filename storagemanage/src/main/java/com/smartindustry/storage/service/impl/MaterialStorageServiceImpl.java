package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smartindustry.common.bo.MaterialStorageBO;
import com.smartindustry.common.bo.PrintLabelBO;
import com.smartindustry.common.mapper.MaterialStorageMapper;
import com.smartindustry.common.mapper.PrintLabelMapper;
import com.smartindustry.common.mapper.StorageLocationMapper;
import com.smartindustry.common.pojo.PrintLabelPO;
import com.smartindustry.common.pojo.StorageLocationPO;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IMaterialStorageService;
import com.smartindustry.storage.vo.MaterialStoragePageVO;
import com.smartindustry.storage.vo.StorageLabelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 11:41
 * @description: 物料入库
 * @version: 1.0
 */
@Service
public class MaterialStorageServiceImpl implements IMaterialStorageService {
    @Autowired
    private MaterialStorageMapper materialStorageMapper;
    @Autowired
    private StorageLocationMapper storageLocationMapper;
    @Autowired
    private PrintLabelMapper printLabelMapper;

    @Override
    public ResultVO pageQuery(int pageNum, int pageSize, Map<String, Object> reqData) {
        Page<MaterialStorageBO> page = PageHelper.startPage(pageNum, pageSize);
        List<MaterialStorageBO> bos = materialStorageMapper.pageQuery(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialStoragePageVO.convert(bos)));
    }

    @Override
    public ResultVO location(String lno) {
        StorageLocationPO locationPO = storageLocationMapper.selectByPrimaryKey(lno);
        if (null == locationPO) {
            return new ResultVO(2000);
        }

        return ResultVO.ok();
    }

    @Override
    public ResultVO label(Long rbid, String pid) {
        PrintLabelBO bo = printLabelMapper.queryByRbidAndPid(rbid, pid);
        if (null == bo) {
            return new ResultVO(2000);
        }

        return ResultVO.ok().setData(StorageLabelVO.convert(bo));
    }
}
