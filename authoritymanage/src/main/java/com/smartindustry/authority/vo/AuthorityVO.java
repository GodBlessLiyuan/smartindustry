package com.smartindustry.authority.vo;

import com.smartindustry.common.bo.am.AuthorityBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:09 2020/8/5
 * @version: 1.0.0
 * @description:
 */
@Data
public class AuthorityVO implements Cloneable,Serializable{
    private static final long SerialVersionUID = 1L;

    private Long aid;
    private String aname;
    private Byte type;
    /**
     * 子权限列表
     */
    private List<AuthorityVO> children;

    public static List<AuthorityVO> convert(List<AuthorityBO> bos) {
        List<AuthorityVO> vos = new ArrayList<>(bos.size());
        for (AuthorityBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static AuthorityVO convert(AuthorityBO bo) {
        AuthorityVO vo = new AuthorityVO();
        vo.setAid(bo.getAuthorityId());
        vo.setAname(bo.getAuthorityName());
        vo.setType(bo.getType());
        return vo;
    }

    @Override
    public Object clone() {
        AuthorityVO o = null;
        try {
            o = (AuthorityVO) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e.toString());
        }
        return o;
    }
}
