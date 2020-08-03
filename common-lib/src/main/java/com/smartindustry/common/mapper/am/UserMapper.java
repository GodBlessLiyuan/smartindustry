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
     * @param pos
     * @return
     */
    Integer deleteBatch(List<UserPO> pos);

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
}