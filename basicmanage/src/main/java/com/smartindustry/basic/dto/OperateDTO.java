package com.smartindustry.basic.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:32
 * @description: 操作 DTO
 * @version: 1.0
 */
@Data
public class OperateDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long wid;
    private Long lid;
    private Long sid;
    private Long mid;
    private Long bhid;
    private List<Long> bbids;
    /**
     * 客户id
     */
    private Long cid;

    private Long rid;

    private Long soid;

    /**
     * 存放前端传回来的权限列表
     */
    private List<Long> perms;

    public static boolean hasAdmin(OperateDTO dto) {
        return isAdmin(dto.getRid());
    }

    public static boolean isAdmin(Long roleid) {
        return null != roleid && roleid == 1L;
    }
}
