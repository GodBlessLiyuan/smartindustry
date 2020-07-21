package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.om.PickLabelPO;
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
     * @param packageId
     * @return
     */
    PrintLabelBO pickPid(@Param("packageId") String packageId);

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
     * 查看当前工单号的pid,仅仅只有pid
     * @param pickHeadId
     * @return
     */
    List<String> queryReOnlyPid(@Param("pickHeadId") Long pickHeadId);
    /**
     * 根据工单拣货单查看目前每个物料已采用的pid
     * @param pickNo
     * @return
     */
    List<PickHeadBO> queryRealPid(@Param("pickNo") String pickNo);

    /**
     * 查看当前物料并不属于该工单
     * @param pickHeadId
     * @return
     */
    List<String> judgeMaterial(@Param("pickHeadId") Long pickHeadId);

    /**
     * 当扫码出库的时候更新当前物料的已拣货量
     * @param materialNo
     * @param pickNum
     * @return
     */
    int addPickNum(@Param("materialNo") String materialNo,@Param("pickNum") Integer pickNum);

    /**
     * 更新拣货标签表中的所有信息
     * @param pickLabelPo
     * @return
     */
    int insertPickLabel(PickLabelPO pickLabelPo);

    /**
     * 根据当前的工单拣货单
     * @param pickNo
     * @return
     */
    PickHeadPO queryByPickNo(@Param("pickNo") Long pickNo);

    /**
     * 查询当前工单号所有已经推荐的pid,需要在 （拣货量 > 需求量）
     * @param pickNo
     * @return
     */
    List<PickHeadBO> queryAllRePid(@Param("pickNo") String pickNo);

    /**
     * 根据当前工单号查询出所有使用未推荐的pid
     * @param pickNo
     * @return
     */
    List<PickHeadBO> queryNoRecommend(@Param("pickNo") String pickNo);
}