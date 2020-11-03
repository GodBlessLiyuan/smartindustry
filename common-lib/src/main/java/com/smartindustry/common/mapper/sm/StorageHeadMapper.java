package com.smartindustry.common.mapper.sm;

import com.smartindustry.common.bo.om.OutboundHeadBO;
import com.smartindustry.common.bo.sm.StorageHeadBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * StorageHeadMapper继承基类
 */
@Mapper
public interface StorageHeadMapper extends BaseMapper<StorageHeadPO, Long> {

    /**
     * 采购入库单的分页查询
     * @param reqData
     * @return
     */
    List<StorageHeadBO> pageQuery(Map<String, Object> reqData);

    /**
     * 通过采购表头id 查询所有信息
     * @param shid
     * @return
     */
    StorageHeadBO queryByShid(Long shid);
    /**
     * 根据 pda 类型 查询
     *
     * @param type
     * @return
     */
    List<StorageHeadBO> queryPdaByType(Byte type);

    /**
     * 查询和当前时间段
     * @param cdate
     * @return
     */
    StorageHeadPO queryPrepareNo(Date cdate,Date stime,Date etime);

    /**
     * 根据状态查询入库单以及来源类型
     * @param status
     * @return
     */
    List<StorageHeadPO> queryByStatus(Byte status,Byte type);

    /**
     * 根据货物rfid查询入库单
     * @param rfid
     * @return
     */
    StorageHeadPO queryByRfid(String rfid);
}