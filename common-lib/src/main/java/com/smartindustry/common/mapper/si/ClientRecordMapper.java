package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.ClientRecordBO;
import com.smartindustry.common.pojo.si.ClientRecordPO;
import com.smartindustry.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClientRecordMapper继承基类
 */
@Mapper
public interface ClientRecordMapper extends BaseMapper<ClientRecordPO, Long> {
    /**
     * 根据cid查询客户的操作记录
     * @param clientId
     * @return
     */
    List<ClientRecordBO> queryByCid(@Param("clientId") Long clientId);
}