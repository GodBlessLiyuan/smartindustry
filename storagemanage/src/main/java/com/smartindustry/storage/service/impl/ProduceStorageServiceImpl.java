package com.smartindustry.storage.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.sm.MaterialDetailBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.si.LocationPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.StorageDetailDTO;
import com.smartindustry.storage.service.IProduceStorageService;
import com.smartindustry.storage.vo.MaterialDetailVO;
import com.smartindustry.storage.vo.StorageDetailVO;
import com.smartindustry.storage.vo.StorageHeadVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private MaterialMapper materialMapper;
    @Autowired
    private LocationMapper locationMapper;

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
        List<MaterialDetailBO> preList = storageDetailMapper.queryPrepare(dto.getShid());
        // 当前入库单得待入库rfid
        List<String> storeList = storageDetailMapper.querySave(dto.getShid());
        //合并数组
//        preList.addAll(storeList);
        Map<String,Object> map = new HashMap<>();
        map.put("prepare",MaterialDetailVO.convert(preList));
        map.put("stored",storeList);
        map.put("detail", StorageDetailVO.convert(bo));
        return ResultVO.ok().setData(map);
    }

    @Override
    public ResultVO queryDetail(StorageDetailDTO dto){
        List<MaterialDetailBO> bos = storageHeadMapper.queryDetail(dto.getShid(),dto.getLid());
        Map<String,Object> map = new HashMap<>();
        LocationPO locationPO = locationMapper.selectByPrimaryKey(dto.getLid());
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(locationPO.getMaterialId());
        if(!bos.isEmpty()){
            map.put("list",MaterialDetailVO.convert(bos));
            map.put("total",materialPO.getPackageVolume().multiply(new BigDecimal(bos.size())));
        }
        return ResultVO.ok().setData(map);
    }
}
