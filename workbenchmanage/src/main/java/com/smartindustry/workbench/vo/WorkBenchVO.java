package com.smartindustry.workbench.vo;

import com.smartindustry.common.bo.wm.WorkBenchBO;
import com.smartindustry.common.config.FilePathConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */
@Data
public class WorkBenchVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long wbid;

    private Long aid;

    /**
     * 工作台权限名称
     */
    private String bname;
    /**
     * 工作台权限分类
     */
    private Byte btype;
    /**
     * 工作台权限模块
     */
    private Byte bmodule;
    /**
     * 图标路径
     */
    private String ipath;
    /**
     * 权限访问URL路径
     */
    private String upath;
    /**
     * 权限名称
     */
    private String aname;

    /**
     * 需要处理的数量
     */
    private Integer num;

    /**
     * 背景颜色
     */
    private String bcolor;

    public static List<WorkBenchVO> convert(List<WorkBenchBO> bos, String basePath) {

        List<WorkBenchVO> vos = new ArrayList<>(bos.size());
        for (WorkBenchBO bo: bos) {
            vos.add(convert(bo, basePath));
        }
        return vos;
    }

    public static WorkBenchVO convert(WorkBenchBO bo, String basePath) {
        WorkBenchVO vo = new WorkBenchVO();
        vo.setAid(bo.getAuthorityId());
        vo.setAname(bo.getAuthorityName());
        vo.setBmodule(bo.getBenchModule());
        vo.setBname(bo.getBenchName());
        vo.setBtype(bo.getBenchType());

        basePath = basePath== null? "":basePath;
        if (bo.getIconPath() != null) {
            vo.setIpath(basePath+bo.getIconPath());
        }
        vo.setUpath(bo.getUrlPath());
        vo.setWbid(bo.getWorkBenchId());
        vo.setBcolor(bo.getBackgroundColor());
        return vo;
    }

}
