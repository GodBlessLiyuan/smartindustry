package com.smartindustry.authority.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/24 14:23
 * @description: 操作DTO
 * @version: 1.0
 */
@Data
public class OperateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long did;
    private Byte status;
    private Long uid;
    private String password;
    private String name;
    private Long rid;
    private String rname;
    /**
     * 存放前端传回来的权限列表
     */
    private List<Long> perms;

    public static boolean hasAdmin(List<OperateDTO> dtos) {
        for (OperateDTO dto : dtos) {
            if (hasAdmin(dto)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAdmin(OperateDTO dto) {
        return isAdmin(dto.getRid());
    }

    public static boolean isAdmin(Long roleid) {
        return null != roleid && roleid == 1L;
    }
}
