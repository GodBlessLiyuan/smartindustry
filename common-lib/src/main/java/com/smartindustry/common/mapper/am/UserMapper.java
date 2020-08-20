package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.pojo.am.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * UserMapper继承基类
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPO, Long> {

    /**
     * 用户的分页查询
     * @param reqData
     * @return
     */
     List<UserBO> userPageQuery(Map<String, Object> reqData);

    /**
     * 批量更新部门状态
     * @param pos
     * @return
     */
    Integer updateBatch(List<UserPO> pos);

    /**
     * 批量删除部门
     * @param uids
     * @return
     */
    Integer deleteBatch(List<Long> uids);

    /**
     * 查询部门负责人列表
     * @return
     */
    List<UserPO> queryLeader();

    /**
     * 查找所有人的名字
     * @param name
     * @return
     */
    List<UserPO> selectAll(@Param("name") String name);

    /**
     * 根据登入名查询用户
     * @param username
     * @return
     */
    UserPO queryByName(@Param("username") String username);

    /**
     * 判断登入名是否重复
     * @param username
     * @return
     */
    Integer judgeRepeatName(@Param("username") String username,@Param("userId") Long userId);

    /**
     * 根据roleId查询所有的相关用户
     * @param roleId
     * @return
     */
    List<Long> queryUser(@Param("roleId") Long roleId);


    /**
     * 将用户列表的 部门置空
     * @param deptId
     * @return
     */
    Integer updateDeptId(@Param("deptId") Long deptId);

    /**
     * 更新用户密码
     * @param password
     * @param userId
     * @return
     */
    Integer updatePassword(@Param("password") String password,@Param("userId") Long userId);

    /**
     * 查询用户详细的个人信息
     * @param userId
     * @return
     */
    UserBO queryUserMsg(@Param("userId") Long userId);

    /**
     *  查询当前即将删除的角色是否被用户所拥有，若拥有则无法删除
     * @param roleId
     * @return
     */
    List<UserPO> queryUserRole(@Param("roleId") Long roleId);
}