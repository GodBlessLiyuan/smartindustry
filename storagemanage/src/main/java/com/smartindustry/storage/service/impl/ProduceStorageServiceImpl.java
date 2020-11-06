package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.service.IProduceStorageService;
import com.smartindustry.storage.vo.StorageHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    @Autowired
    private StorageDetailMapper storageDetailMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<StorageHeadBO> page = PageQueryUtil.startPage(reqData);
        List<StorageHeadBO> bos = storageHeadMapper.pageQueryPro(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), StorageHeadVO.convert(bos)));
    }

    @Override
    public ResultVO detail(OperateDTO dto){
        //入库单表头信息
        StorageHeadBO bo = storageHeadMapper.queryStored(dto.getShid());
        // 查询待入库物料
        List<StorageDetailPO> preList = storageDetailMapper.queryPrepare(dto.getShid());
        // 当前入库单得待入库rfid
        List<StorageDetailPO> storeList = storageDetailMapper.querySave(dto.getShid());
        //合并数组
        preList.addAll(storeList);

        Map<String,Object> map = new HashMap<>();
        map.put("stored",preList);
        map.put("detail",bo);
        return ResultVO.ok().setData(map);
    }
}
