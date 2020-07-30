package com.smartindustry.common.mapper.si;

import com.smartindustry.common.bo.si.SupplierBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.si.SupplierPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SupplierMapper继承基类
 */
@Mapper
public interface SupplierMapper extends BaseMapper<SupplierPO, Long> {
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    List<SupplierBO> pageQuery(Map<String, Object> reqData);

    /**
     * 根据 编号 查询
     *
     * @param sno
     * @return
     */
    SupplierPO queryBySno(String sno);

    /**
     * 批量删除
     *
     * @param sids
     */
    void batchDelete(List<Long> sids);

    /**
     * 根据sid 查询
     *
     * @param sid
     * @return
     */
    SupplierBO queryBySid(Long sid);

    /**
     * 根据 sgid 查询
     *
     * @param sgid
     * @return
     */
    List<SupplierPO> queryBySgid(Long sgid);

    /**
     * 根据 csid 查询
     *
     * @param csid
     * @return
     */
    List<SupplierPO> queryByCsid(Long csid);

    /**
     * 根据 stid 查询
     *
     * @param stid
     * @return
     */
    List<SupplierPO> queryByStid(Long stid);

    /**
     * 根据 spid 查询
     *
     * @param spid
     * @return
     */
    List<SupplierPO> queryBySpid(Long spid);

    /**
     * 根据 cid 查询
     *
     * @param cid
     * @return
     */
    List<SupplierPO> queryByCid(Long cid);
}