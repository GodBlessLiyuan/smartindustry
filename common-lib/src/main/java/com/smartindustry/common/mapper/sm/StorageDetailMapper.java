package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * StorageDetailMapper继承基类
 */
@Mapper
public interface StorageDetailMapper extends BaseMapper<StorageDetailPO, Long> {
    StorageDetailPO queryByRfidAndStatus(@Param("rfid") String rfid, @Param("status") byte status);

    /**
     * 根据库位id和栈板rfid删除入库详情
     * @param lid
     * @param rfid
     */
    void deleteDetail(Long lid,String rfid);

    /**
     * 根据储位id查询当前栈板数
     * @param lid
     * @return
     */
    BigDecimal queryStored(Long lid);
}