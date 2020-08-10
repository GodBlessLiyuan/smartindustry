package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RoleAuthorityPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RoleAuthorityMapper继承基类
 */

@Mapper
public interface RoleAuthorityMapper extends BaseMapper<RoleAuthorityPO, Long> {

    /**
     * 根据角色id删除角色权限表中的权限
     * @param roleId
     * @return
     */
    Integer deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量新增角色权限
     * @param roleId
     * @param perms
     * @return
     */
    Integer insertBatch(@Param("roleId") Long roleId,@Param("perms") List<Long> perms);


    /**
     * 查找角色所拥有的权限
     * @param roleId
     * @return
     */
    List<Long> queryByRoleId(@Param("roleId") Long roleId);
}