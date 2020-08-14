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
public interface IMaterialItemsService {

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
     * 展示主bom的层级结构
     * @param dto
     * @return
     */
    ResultVO queryTreeBom(OperateDTO dto);

    /**
     * 新增物料明细
     * @param dto
     * @return
     */
    ResultVO insertDetail(BomBodyDTO dto);

    /**
     * 查询物料属性列表
     * @return
     */
    ResultVO queryProperty(MaterialPropertyDTO dto);

    /**
     *查询物料工序列表
     */
    ResultVO queryProcess(ProcessDTO dto);

    /**
     * 新增物料属性
     * @param dto
     * @return
     */
    ResultVO insertProperty(MaterialPropertyDTO dto);


    /**
     * 新增工序
     * @param dto
     * @return
     */
    ResultVO insertProcess(ProcessDTO dto);

    /**
     * 根据主BOM的ID查询子物料清单明细
     * @param dto
     * @return
     */
    ResultVO queryBomBody(OperateDTO dto);


    /**
     * 修改/更新 物料明细表
     * @param dto
     * @return
     */
    ResultVO updateDetail(BomBodyDTO dto);
}
