package com.smartindustry.authority.vo;

import com.smartindustry.common.bo.am.LoginUserBO;
import com.smartindustry.common.pojo.am.UserPO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 8:42 2020/9/21
 * @version: 1.0.0
 * @description:
 */
@Data
public class LoginUserVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 用户信息
     */
    private UserVO user;

    /**
     * 权限列表ID
     */
    private List<Long> permissionIds;

    public static LoginUserVO convert(LoginUserBO bo) {
        LoginUserVO vo = new LoginUserVO();
        UserVO userVO = new UserVO();
        vo.setPermissionIds(bo.getPermissionIds());
        userVO.setUid(bo.getUser().getUserId());
        userVO.setUname(bo.getUser().getUsername());
        vo.setUser(userVO);
        return vo;
    }
}
