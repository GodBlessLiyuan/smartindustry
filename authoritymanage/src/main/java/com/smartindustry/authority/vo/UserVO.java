package com.smartindustry.authority.vo;

import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.pojo.am.UserPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:11 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 登录名
     */
    private String uname;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 所属部门
     */
    private String dname;

    private String phone;

    private Byte status;

    public static List<UserVO> convert(List<UserBO> bos) {
        List<UserVO> vos = new ArrayList<>(bos.size());
        for (UserBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static UserVO convert(UserBO bo) {
        UserVO vo = new UserVO();
        vo.setDname(bo.getDeptName());
        vo.setName(bo.getName());
        vo.setPhone(bo.getPhone());
        vo.setStatus(bo.getStatus());
        vo.setUid(bo.getUserId());
        vo.setUname(bo.getUsername());
        return vo;
    }


    public static List<UserVO> convertPO(List<UserPO> pos) {
        List<UserVO> vos = new ArrayList<>(pos.size());
        for (UserPO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    public static UserVO convert(UserPO po) {
        UserVO vo = new UserVO();
        vo.setUid(po.getUserId());
        vo.setUname(po.getName());
        return vo;
    }
}
