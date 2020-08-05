package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.AuthorityBO;
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
    List<String> queryPermsByUserId(@Param("userId") Long userId);

    /**
     * 查询超级管理员的所有权限
     * @return
     */
    List<String> queryAllPerm();

    /**
     * 判断当前节点是否有父节点，没有那么其本身作为子节点
     * @param parentId
     * @return
     */
    Integer judgeExist(@Param("parentId") Long parentId);

    /**
     * 查看当前节点的子节点列表
     * @param parentId
     * @param userId
     * @return
     */
    List<AuthorityBO> queryChildren(@Param("parentId") Long parentId,@Param("userId") Long userId);
}