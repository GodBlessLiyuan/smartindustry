package com.smartindustry.workbench.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.dto.OperateDTO;

/**
 * @author hui.feng
 * @date created in 2020/9/25
 * @description
 */
public interface IWorkBenchService {

    /**
     * 获取用户在只能工作台的权限列表
     *
     * @param dto
     * @return
     */
    ResultVO work(OperateDTO dto);


    /**
     * 获取用户在快捷访问中的模块列表
     *
     * @param dto
     * @return
     */
    ResultVO shortcut(OperateDTO dto);
}
