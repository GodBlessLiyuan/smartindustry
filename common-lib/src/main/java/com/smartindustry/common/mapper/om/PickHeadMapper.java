package com.smartindustry.common.mapper.om;

import com.smartindustry.common.bo.om.MaterialBO;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.bo.om.PickHeadBO;
import com.smartindustry.common.bo.si.PrintLabelBO;
import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.om.PickLabelPO;
import com.smartindustry.common.pojo.si.PrintLabelPO;
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
     * 成品拣货单查看
     * @param reqMap
     * @return
     */
    List<PickHeadPO> queryPickGoods(Map<String, Object> reqMap);

    /**
     * OQC检测查询
     * @param pickNo
     * @param orderNo
     * @return
     */
    List<PickHeadPO> queryOqc(@Param("pickNo") String pickNo,@Param("orderNo") String orderNo);

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
     * @param pickHeadId
     * @return
     */
    List<PickHeadBO> queryRecommend(@Param("pickHeadId") Long pickHeadId);

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
    int addPickNum(@Param("pickHeadId") Long pickHeadId,@Param("materialNo") String materialNo,@Param("pickNum") Integer pickNum);

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
     * @param pickHeadId
     * @return
     */
    List<PickHeadBO> queryAllRePid(@Param("pickHeadId") Long pickHeadId);

    /**
     * 根据当前工单号查询出所有使用未推荐的pid
     * @param pickHeadId
     * @return
     */
    List<PickHeadBO> queryNoRecommend(@Param("pickHeadId") Long pickHeadId);

    /**
     * 展示扫码拣货下面的已扫码列表
     * @param pickHeadId
     * @return
     */
    List<PrintLabelBO> showScanItems(@Param("pickHeadId") Long pickHeadId);

    /**
     * 扫码拣货列表选中的批量删除
     * @param pickHeadId
     * @param printLabelId
     * @return
     */
    int deleteScanPid(@Param("pickHeadId") Long pickHeadId,@Param("printLabelId") Long printLabelId);

    /**
     * 分料后的关联pid查询
     * @param packageId
     * @return
     */
    List<PrintLabelBO> printLabelSplit(@Param("packageId") String packageId);

    /**
     * 更新物料状态
     * @param pickHeadId
     * @param materialStatus
     * @return
     */
    int updateStatus(@Param("pickHeadId") Long pickHeadId,@Param("materialStatus") Byte materialStatus);

    /**
     * 更新出库状态
     * @param pickHeadId
     * @param outboundStatus
     * @return
     */
    int updateOutStatus(@Param("pickHeadId") Long pickHeadId,@Param("outboundStatus") Byte outboundStatus);

    /**
     * 判断当前的工单物料状态是不是物料拣货状态
     * @param pickHeadId
     * @return
     */
    int judgeIsPick(@Param("pickHeadId") Long pickHeadId);

    /**
     * 判断当前的工单拣货单是否是异常
     * @param pickHeadId
     * @return
     */
    Integer judgeIsEx(@Param("pickHeadId") Long pickHeadId);

    /**
     * 判断当前工单是否存在物料欠料
     * @param pickHeadId
     * @return
     */
    Integer judgeIsLack(@Param("pickHeadId") Long pickHeadId);

    /**
     * 查看物料和工单是否匹配
     * @param pickHeadId
     * @param materialNo
     * @return
     */
    Integer judgeIsMaHave(@Param("pickHeadId") Long pickHeadId,@Param("materialNo") String materialNo);

    /**
     * 查看pid是否已经在当前的工单拣货单下
     * @param pickHeadId
     * @param packageId
     * @return
     */
    Integer judgeIsPidHave(@Param("pickHeadId") Long pickHeadId,@Param("packageId") String packageId);

    /**
     * 恢复原先的pid
     * @param packageId
     * @return
     */
    int resumePid(@Param("packageId") String packageId);

    /**
     * 删除分料
     * @param packageId
     * @return
     */
    int deletePid(@Param("packageId") String packageId);


    /**
     * 判断某个PID是否已经在某个工单的扫码列表下
     * @param packageId
     * @return
     */
    Integer judgePidInPhid(@Param("packageId") String packageId);

    /**
     * 欠料出库单审核的查询
     * @param reqData
     * @return
     */
    List<PickHeadPO> outOrderCheck(Map<String, Object> reqData);

    /**
     * 在扫码列表中删除一个pid时，相应的已拣货量需要更新
     * @param num
     * @param materialNo
     * @return
     */
    int updatePickNum(@Param("pickHeadId") Long pickHeadId,
                      @Param("num") Integer num,
                      @Param("materialNo") String materialNo);

    /**
     * 查询父pid的两个子节点
     * @param packageId
     * @return
     */
    List<String> queryChildPid(@Param("packageId") String packageId);


    /**
     * 查询当前工单拣货单id下得PID列表
     * @param pickHeadId
     * @return
     */
    List<PrintLabelPO> queryByPhidItems(@Param("pickHeadId") Long pickHeadId);
}