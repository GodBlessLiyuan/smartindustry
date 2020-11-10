package com.smartindustry.common.mapper.im;

import com.smartindustry.common.bo.im.MaterialInventoryBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.im.MaterialInventoryPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MaterialInventoryMapper继承基类
 */
@Mapper
public interface MaterialInventoryMapper extends BaseMapper<MaterialInventoryPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<Long> pageQuery(Map<String, Object> reqData);

    /**
     * 批量删除
     *
     * @param mids
     */
    void batchDelete(List<Long> mids);

    /**
     * 根据Miid查询
     *
     * @param miids
     * @return
     */
    List<MaterialInventoryBO> queryByMiids(List<Long> miids);
}