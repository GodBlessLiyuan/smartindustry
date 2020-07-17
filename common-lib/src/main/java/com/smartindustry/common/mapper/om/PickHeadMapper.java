package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PickHeadMapper继承基类
 */
@Mapper
public interface PickHeadMapper extends BaseMapper<PickHeadPO, Long> {
    /**
     * 查询拣货单表头的某些数据项
     *
     * @param reqMap
     * @return
     */
    List<PickHeadPO> pageQueryPickHeadMsg(Map<String, Object> reqMap);

    /**
     * 根据工单拣货单号和PID扫码出库
     * @param pickNo
     * @param packageId
     * @return
     */
    PrintLabelBO pickPid(@Param("pickNo") String pickNo, @Param("packageId") String packageId);

    /**
     * 扫码拣货的欠料的列表
     * @param reqMap
     * @return
     */
    List<MaterialBO> materialLoss(Map<String, Object> reqMap);

    /**
     * 查看当前工单拣货单的推荐pid格式为  pickNo,maNo,(pid1,pid2,pid3)
     * @param pickNo
     * @return
     */
    List<PickHeadBO> queryRecommend(@Param("pickNo") String pickNo);


    /**
     * 根据工单拣货单查看目前每个物料已采用的pid
     * @param pickNo
     * @return
     */
    List<PickHeadBO> queryRealPid(@Param("pickNo") String pickNo);

    /**
     * 查看当前物料并不属于该工单
     * @param pickNo
     * @param pid
     * @return
     */
    List<PickHeadPO> judgeMaterial(@Param("pickNo") String pickNo,@Param("pid") String pid);
}