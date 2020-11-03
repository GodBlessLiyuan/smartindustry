package com.smartindustry.common.mapper.em;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.em.TransferHeadPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * TransferHeadMapper继承基类
 */
@Mapper
public interface TransferHeadMapper extends BaseMapper<TransferHeadPO, Long> {

    /**
     * 根据入库id 查看调拨信息
     * @param storageId
     * @return
     */
    TransferHeadPO queryBySid(Long storageId);
}