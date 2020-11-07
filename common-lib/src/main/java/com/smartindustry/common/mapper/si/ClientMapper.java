package com.smartindustry.common.mapper.si;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.ClientPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClientMapper继承基类
 */
@Mapper
public interface ClientMapper extends BaseMapper<ClientPO, Long> {
    /**
     * 根据客户编号查找客户
     * @param clientNo
     * @return
     */
    ClientPO queryByClientNo(@Param("clientNo") String clientNo);

    /**
     * 分页查询
     * @param reqData
     * @return
     */
    List<ClientPO> pageQuery(Map<String, Object> reqData);
}