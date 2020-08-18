package com.smartindustry.inventory.controller;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.inventory.dto.MaterialDetailDTO;
import com.smartindustry.inventory.service.IMaterialDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/8/10 16:03
 * @description: 物料库存明细查询
 * @version: 1.0
 */
@RequestMapping("detail")
@RestController
public class MaterialDetailController {
    @Autowired
    private IMaterialDetailService materialDetailService;

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('im:info:gquery:query,im:info:mquery:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialDetailService.pageQuery(reqData);
    }

    /**
     * 物料锁定
     */
    @PostMapping("lock")
    @PreAuthorize("@ss.hasPermi('im:info:gquery:lock,im:info:gquery:unlock,im:info:mquery:lock,im:info:mquery:unlock')")
    public ResultVO lock(@RequestBody MaterialDetailDTO dto) {
        return materialDetailService.lock(dto);
    }
}
