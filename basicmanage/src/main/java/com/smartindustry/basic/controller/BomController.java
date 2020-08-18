package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.dto.ProcessDTO;
import com.smartindustry.basic.dto.MaterialPropertyDTO;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.basic.service.IBomService;
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
 * @version: 1.0.0im:info:mquery:unlock
 * @description: 物料清单管理
 */
@RequestMapping("items")
@RestController
public class BomController {
    @Autowired
    IBomService itemsService;
    @Autowired
    IDataDictionaryService dataDictionaryService;
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

    @PostMapping("deleteBody")
    public ResultVO deleteBody(@RequestBody List<Long> bbids) {
        return itemsService.deleteBody(bbids);
    }

    @PostMapping("queryTreeBom")
    public ResultVO queryTreeBom(@RequestBody OperateDTO dto) {
        return itemsService.queryTreeBom(dto);
    }

    @PostMapping("insertDetail")
    public ResultVO insertDetail(@RequestBody BomBodyDTO dto) {
        return itemsService.insertDetail(dto);
    }

    @PostMapping("queryBomBody")
    public ResultVO queryBomBody(@RequestBody Map<String, Object> reqData) {
        return itemsService.queryBomBody(reqData);
    }

    @PostMapping("updateDetail")
    public ResultVO updateDetail(@RequestBody BomBodyDTO dto) {
        return itemsService.updateDetail(dto);
    }
}
