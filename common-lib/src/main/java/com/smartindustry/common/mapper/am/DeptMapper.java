package com.smartindustry.common.mapper.am;

import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * DeptMapper继承基类
 */

@Mapper
public interface DeptMapper extends BaseMapper<DeptPO, Long> {

    /**
     * 部门查询
     * @param reqData
     * @return
     */
    List<DeptBO> deptPageQuery(Map<String, Object> reqData);

    /**
     * 批量更新部门状态
     * @param pos
     * @return
     */
    Integer updateBatch(List<DeptPO> pos);

    /**
     * 批量删除部门
     * @param pos
     * @return
     */
    Integer deleteBatch(List<Long> pos);


    /**
     * 判断当前节点是否有父节点，没有那么其本身作为子节点
     * @param parentId
     * @return
     */
    Integer judgeExist(@Param("parentId") Long parentId);

    /**
     * 查询当前节点的子节点
     * @param parentId
     * @return
     */
    List<DeptBO> queryChildren(@Param("parentId") Long parentId);

    /**
     * 判断当前部门name是否存在/重复
     * @param deptName
     * @return
     */
    Integer judgeRepeatName(@Param("deptName") String deptName,@Param("deptId") Long deptId);
}