package com.smartindustry.basic.controller;

import com.smartindustry.basic.dto.MaterialDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IMaterialService;
import com.smartindustry.common.vo.ResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:18
 * @description: 物料管理
 * @version: 1.0
 */
@RequestMapping("material")
@RestController
public class MaterialController {
    @Autowired
    private IMaterialService materialService;

    /**
     * 分页查询
     *
     * @param reqData
     * @return
     */
    @PostMapping("pageQuery")
    @PreAuthorize("@ss.hasPermi('bm:mm:main:query')")
    public ResultVO pageQuery(@RequestBody Map<String, Object> reqData) {
        return materialService.pageQuery(reqData);
    }

    /**
     * 新增/修改
     *
     * @return
     */
    @PostMapping("edit")
    @PreAuthorize("@ss.hasAnyPermi('bm:mm:main:update,bm:mm:main:insert')")
    public ResultVO edit(@RequestBody MaterialDTO dto) {
        return materialService.edit(dto);
    }
    /**
     * 删除
     *
     * @param mids
     * @return
     */
    @PostMapping("delete")
    @PreAuthorize("@ss.hasPermi('bm:mm:main:delete')")
    public ResultVO delete(@RequestBody List<Long> mids) {
        return materialService.delete(mids);
    }

    /**
     * 查看详情
     *
     * @param dto
     * @return
     */
    @PostMapping("detail")
    @PreAuthorize("@ss.hasAnyPermi('bm:mm:main:queryinfo,bm:mm:main:update')")
    public ResultVO detail(@RequestBody OperateDTO dto) {
        return materialService.detail(dto);
    }
    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("upload")
    @PreAuthorize("@ss.hasPermi('bm:mm:main:update')")
    public ResultVO upload(@Param("file") MultipartFile file) {
        return materialService.upload(file);
    }

}
