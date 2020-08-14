package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.ProcessDTO;
import com.smartindustry.basic.dto.PropertyDTO;
import com.smartindustry.basic.service.IMaterialItemsService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 10:23 2020/8/13
 * @version: 1.0.0
 * @description: 物料清单管理
 */
@RequestMapping("items")
@RestController
public class MaterialItemsController {
    @Autowired
    IMaterialItemsService itemsService;
    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return itemsService.pageQuery(reqData);
    }

    /**
     * 用于BOM添加的物料展示
     * @param reqData
     * @return
     */
    @PostMapping("pageQueryBom")
    public ResultVO pageQueryBom(@RequestBody Map<String, Object> reqData) {
        return itemsService.pageQueryBom(reqData);
    }

    @PostMapping("insert")
    public ResultVO insert(@RequestBody OperateDTO dto) {
        return itemsService.insert(dto);
    }

    @PostMapping("delete")
    public ResultVO delete(@RequestBody List<Long> bhids) {
        return itemsService.delete(bhids);
    }

    @PostMapping("queryTreeBom")
    public ResultVO queryTreeBom(@RequestBody OperateDTO dto) {
        return itemsService.queryTreeBom(dto);
    }

    @PostMapping("insertDetail")
    public ResultVO insertDetail(@RequestBody BomBodyDTO dto) {
        return itemsService.insertDetail(dto);
    }

    @PostMapping("queryProperty")
    public ResultVO queryProperty(@RequestBody OperateDTO dto) {
        return itemsService.queryProperty(dto);
    }

    @PostMapping("queryProcess")
    public ResultVO queryProcess(@RequestBody OperateDTO dto) {
        return itemsService.queryProcess(dto);
    }

    @PostMapping("insertProperty")
    public ResultVO insertProperty(@RequestBody PropertyDTO dto) {
        return itemsService.insertProperty(dto);
    }

    @PostMapping("insertProcess")
    public ResultVO insertProcess(@RequestBody ProcessDTO dto) {
        return itemsService.insertProcess(dto);
    }

    @PostMapping("queryBomBody")
    public ResultVO queryBomBody(@RequestBody OperateDTO dto) {
        return itemsService.queryBomBody(dto);
    }

    @PostMapping("updateDetail")
    public ResultVO updateDetail(@RequestBody BomBodyDTO dto) {
        return itemsService.updateDetail(dto);
    }
}
