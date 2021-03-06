package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ForkliftMapper继承基类
 */
@Mapper
public interface ForkliftMapper extends BaseMapper<ForkliftPO, Long> {
    /**
     * 叉车的分页查询
     *
     * @param reqData
     * @return
     */
    List<ForkliftPO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据 IMEI 查询
     *
     * @param imei
     * @return
     */
    ForkliftPO queryByImei(String imei);

    /**
     * 根据 入库表头ID 查询
     *
     * @param shid
     * @return
     */
    List<ForkliftPO> queryByShid(Long shid);

    /***
     * 假删除
     * @param forkliftIdS
     */
    void deleteDr(List<Long> forkliftIdS);


}