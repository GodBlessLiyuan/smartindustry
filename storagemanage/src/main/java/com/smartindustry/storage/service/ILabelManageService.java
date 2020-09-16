package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.OperateDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:06
 * @description: 标签管理
 * @version: 1.0
 */
public interface ILabelManageService {
    /**
     * 查询
     *
     * @param dto
     * @return
     */
    ResultVO query(@RequestBody OperateDTO dto);

    /**
     * 根据 PID 查询
     *
     * @param dto
     * @return
     */
    ResultVO queryPid(@RequestBody OperateDTO dto);

    /**
     * 打印
     *
     * @param dto
     * @return
     */
    ResultVO print(@RequestBody OperateDTO dto);

    /**
     * 重新打印
     *
     * @param dto
     * @return
     */
    ResultVO reprint(OperateDTO dto);

    /**
     * 标签录入
     *
     * @param dto
     * @return
     */
    ResultVO insert(PrintLabelDTO dto);

    /**
     * 删除
     *
     * @param dto
     * @return
     */
    ResultVO delete(@RequestBody OperateDTO dto);

    /**
     * 录入完成
     *
     * @param dto
     * @return
     */
    ResultVO finish(@RequestBody OperateDTO dto);

    /**
     * 标签拆分
     *
     * @param dto
     * @return
     */
    ResultVO split(LabelSplitDTO dto);

    /**
     * 根据物料单ID 获取待入库物料列表
     *
     * @param dto
     * @return
     */
    ResultVO queryRbid(OperateDTO dto);
}
