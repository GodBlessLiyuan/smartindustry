package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IProduceStorageService;
import com.smartindustry.storage.vo.StorageHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:01 2020/11/4
 * @version: 1.0.0
 * @description:
 */
@Service
public class ProduceStorageServiceImpl implements IProduceStorageService {
    @Autowired
    private StorageHeadMapper storageHeadMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<StorageHeadBO> page = PageQueryUtil.startPage(reqData);
        List<StorageHeadBO> bos = storageHeadMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StorageHeadVO.convert(bos)));
    }
}
