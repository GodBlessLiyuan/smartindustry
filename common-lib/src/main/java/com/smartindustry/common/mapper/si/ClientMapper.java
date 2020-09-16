package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ClientPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ClientMapper继承基类
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientPO, Long> {

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<ClientBO> pageQuery(Map<String, Object> reqData);

    /**
     * 批量删除
     *
     * @param cids
     */
    void batchDelete(List<Long> cids);
}