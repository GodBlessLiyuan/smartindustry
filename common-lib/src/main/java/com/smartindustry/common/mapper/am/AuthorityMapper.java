package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.AuthorityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AuthorityMapper继承基类
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<AuthorityPO, Long> {

    /**
     * 根据userid获取到当前用户的所有权限
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(@Param("userId") Long userId);
}