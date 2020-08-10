package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.UserAuthorityBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.MUserAuthorityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MUserAuthorityMapper继承基类
 */
@Mapper
public interface MUserAuthorityMapper extends BaseMapper<MUserAuthorityPO, Long> {
    /**
     * 查询当前用户的父节点下的所有子节点列表
     * @param userId
     * @param parentId
     * @return
     */
    List<UserAuthorityBO> queryTreeById(@Param("userId") Long userId, @Param("parentId") Long parentId);

    /**
     * 根据用户id删除角色权限表中的权限
     * @param userId
     * @return
     */
    Integer deleteByUserId(@Param("userId") Long userId);

    /**
     * 批量新增角色权限
     * @param userId
     * @param perms
     * @return
     */
    Integer insertBatch(@Param("userId") Long userId,@Param("perms") List<Long> perms);
}