package com.smartindustry.authority.dto;

import com.smartindustry.authority.constant.AuthorityConstant;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.util.SecurityUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 19:23 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserDTO {
    private Long uid;
    private String name;
    private String uname;
    private Long did;
    private String password;
    private Long rid;
    private String job;
    private String phone;
    private String email;
    private Byte status;
    private Byte sex;
    private String remark;
    private List<Long> dids;

    public static UserPO buildPO(OperateDTO dto) {
        UserPO po = new UserPO();
        po.setDeptId(dto.getDid());
        po.setStatus(dto.getStatus());
        po.setUpdateTime(new Date());
        if(dto.getPassword()!=null){
            po.setPassword(SecurityUtil.encryptPassword(dto.getPassword()));
        }
        po.setUserId(dto.getUid());
        return po;
    }

    public static UserPO createPO(UserDTO dto) {
        UserPO po = new UserPO();
        po.setStatus(dto.getStatus());
        if (dto.getDids()!=null){
            po.setDeptId(dto.getDids().get(dto.getDids().size()-1));
        }
        po.setDr((byte)1);
        po.setUserId(dto.getUid());
        po.setEmail(dto.getEmail());
        if(dto.getPassword()!=null){
            po.setPassword(SecurityUtil.encryptPassword(dto.getPassword()));
        }
        po.setJob(dto.getJob());
        po.setCreateTime(new Date());
        po.setName(dto.getName());
        po.setSex(dto.getSex());
        po.setUsername(dto.getUname());
        po.setRoleId(dto.getRid());
        po.setPhone(dto.getPhone());
        po.setRemark(dto.getRemark());
        return po;
    }

    public static List<UserPO> updateList(List<OperateDTO> dtos){
        List<UserPO> pos = new ArrayList<>(dtos.size());
        for(OperateDTO dto:dtos) {
            pos.add(buildPO(dto));
        }
        return pos;
    }
}
