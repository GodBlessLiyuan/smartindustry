package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.BomBodyDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IDataDictionaryService;
import com.smartindustry.basic.service.IBomService;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@ss.hasPermi('im:ml:mm:query')")
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
    @PreAuthorize("@ss.hasAnyPermi('im:ml:mm:insert,im:ml:mm:edit')")
    public ResultVO pageQueryBom(@RequestBody Map<String, Object> reqData) {
        return itemsService.pageQueryBom(reqData);
    }

    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('im:ml:mm:insert')")
    public ResultVO insert(@RequestBody OperateDTO dto) {
        return itemsService.insert(dto);
    }

    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('im:ml:mm:delete')")
    public ResultVO delete(@RequestBody List<Long> bhids) {
        return itemsService.delete(bhids);
    }

    @PostMapping("deleteBody")
    @PreAuthorize("@ss.hasPermi('im:ml:mm:edit')")
    public ResultVO deleteBody(@RequestBody List<Long> bbids) {
        return itemsService.deleteBody(bbids);
    }

    @PostMapping("queryTreeBom")
    @PreAuthorize("@ss.hasAnyPermi('im:ml:mm:queryinfo,im:ml:mm:edit')")
    public ResultVO queryTreeBom(@RequestBody OperateDTO dto) {
        return itemsService.queryTreeBom(dto);
    }

    @PostMapping("editDetail")
    @PreAuthorize("@ss.hasPermi('im:ml:mm:edit')")
    public ResultVO editDetail(@RequestBody BomBodyDTO dto) {
        return itemsService.editDetail(dto);
    }

    @PostMapping("queryBomBody")
    @PreAuthorize("@ss.hasAnyPermi('im:ml:mm:queryinfo,im:ml:mm:edit')")
    public ResultVO queryBomBody(@RequestBody Map<String, Object> reqData) {
        return itemsService.queryBomBody(reqData);
    }

    @PostMapping("queryBomRecord")
    public ResultVO queryBomRecord(@RequestBody OperateDTO dto) {
        return itemsService.queryBomRecord(dto);
    }
}
