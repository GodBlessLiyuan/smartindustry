package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.ClientDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:35 2020/9/16
 * @version: 1.0.0
 * @description:
 */
public interface IClientService {

    /**
     * 客户的 分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 批量删除客户
     * @param cids
     * @return
     */
    ResultVO delete(List<Long> cids);

    /**
     * 客户明细编辑
     * @param dto
     * @return
     */
    ResultVO edit(ClientDTO dto);

    /**
     * 查询操作记录
     * @param dto
     * @return
     */
    ResultVO queryRecord(OperateDTO dto);

    /**
     * 查询当前客户的详细信息
     * @param dto
     * @return
     */
    ResultVO query(OperateDTO dto);
}
