package com.smartindustry.storage.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelSplitDTO;
import com.smartindustry.storage.dto.PrintLabelDTO;

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
     * @param rbId
     * @return
     */
    ResultVO query(Long rbId);

    /**
     * 根据 PID 查询
     *
     * @param rbId
     * @param pid
     * @return
     */
    ResultVO queryPid(Long rbId, String pid);

    /**
     * 打印
     *
     * @param pid
     * @return
     */
    ResultVO print(String pid, Byte status);

    /**
     * 重新打印
     *
     * @param plId
     * @param num
     * @return
     */
    ResultVO reprint(Long plId, Integer num);

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
     * @param plId
     * @return
     */
    ResultVO delete(Long rbId, Long plId);

    /**
     * 录入完成
     *
     * @param rbId
     * @return
     */
    ResultVO finish(Long rbId);

    /**
     * 标签拆分
     *
     * @param dto
     * @return
     */
    ResultVO split(LabelSplitDTO dto);
}
