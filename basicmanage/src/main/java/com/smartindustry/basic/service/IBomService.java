package com.smartindustry.basic.service;

import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.ProcessDTO;
import com.smartindustry.basic.dto.MaterialPropertyDTO;
import com.smartindustry.common.vo.ResultVO;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:27 2020/8/13
 * @version: 1.0.0
 * @description:
 */
public interface IBomService {

    /**
     * 物料清单管理的 分页查询
     * @param reqData
     * @return
     */
    ResultVO pageQuery(Map<String, Object> reqData);

    /**
     * 新增主BOM清单时的 物料展示
     * @param reqData
     * @return
     */
    ResultVO pageQueryBom(Map<String, Object> reqData);

    /**
     * 新增主BOM清单
     * @param dto
     * @return
     */
    ResultVO insert(OperateDTO dto);

    /**
     * 删除主物料清单
     * @param bhids
     * @return
     */
    ResultVO delete(List<Long> bhids);

    /**
     * 批量删除物料明细
     * @param bbids
     * @return
     */
    ResultVO deleteBody(List<Long> bbids);

    /**
     * 展示主bom的层级结构
     * @param dto
     * @return
     */
    ResultVO queryTreeBom(OperateDTO dto);

    /**
     * 新增/修改物料明细
     * @param dto
     * @return
     */
    ResultVO editDetail(BomBodyDTO dto);



    /**
     * 根据主BOM的ID查询子物料清单明细
     * @param reqData
     * @return
     */
    ResultVO queryBomBody(Map<String, Object> reqData);

    /**
     * 查询BOM清单的操作记录
     * @param dto
     * @return
     */
    ResultVO queryBomRecord(OperateDTO dto);

}
