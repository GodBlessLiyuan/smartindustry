package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.am.UserBO;
import com.smartindustry.common.pojo.am.UserPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

    private Long did;
    /**
     * 所属部门
     */
    private String dname;

    private Long rid;

    private String rname;

    private Date ctime;

    private String userCode;

    private List<Long> dcode;
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
        vo.setUid(bo.getUserId());
        vo.setUname(bo.getUsername());
        vo.setDid(bo.getDeptId());
        vo.setRname(bo.getRoleName());
        vo.setRid(bo.getRoleId());
        vo.setCtime(bo.getCreateTime());
        vo.setUserCode(bo.getUserCode());
        return vo;
    }

    public static UserVO convertToUserMsg(UserBO bo) {
        UserVO vo = new UserVO();
        vo.setName(bo.getName());
        vo.setUname(bo.getUsername());
        vo.setDname(bo.getDeptName());
        vo.setRname(bo.getRoleName());
        vo.setCtime(bo.getCreateTime());
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
