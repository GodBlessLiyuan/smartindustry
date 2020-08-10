package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.WarehouseBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.WarehousePO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * WarehouseMapper继承基类
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<WarehousePO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<WarehouseBO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据ID查询
     *
     * @param wid
     * @return
     */
    WarehouseBO queryById(Long wid);

    /**
     * 根据编号查询
     *
     * @param wno
     * @return
     */
    WarehousePO queryByNo(String wno);

    /**
     * 批量删除
     *
     * @param wids
     */
    void batchDelete(List<Long> wids);

    /**
     * 根据 wtid 查询
     *
     * @param wtid
     * @return
     */
    List<WarehousePO> queryByWtid(Long wtid);

    /**
     * 查询所有
     *
     * @return
     */
    List<Map<String, Object>> queryAll();
}