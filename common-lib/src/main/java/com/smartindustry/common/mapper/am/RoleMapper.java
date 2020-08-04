package com.smartindustry.common.mapper.am;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.RolePO;
import com.smartindustry.common.pojo.am.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * RoleMapper继承基类
 */

@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {

    /**
     * 查询所有的角色名列表
     * @param roleName
     * @return
     */
    List<RolePO> selectAll(@Param("roleName") String roleName);

    /**
     * 角色分页模糊查询
     * @param reqData
     * @return
     */
    List<RolePO> rolePageQuery(Map<String, Object> reqData);


    /**
     * 批量更新部门状态
     * @param pos
     * @return
     */
    Integer updateBatch(List<RolePO> pos);

    /**
     * 批量删除
     * @param rids
     * @return
     */
    Integer deleteBatch(List<Long> rids);
}