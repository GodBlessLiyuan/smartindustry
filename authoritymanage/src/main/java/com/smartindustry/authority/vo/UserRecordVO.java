package com.smartindustry.authority.vo;

import com.smartindustry.common.bo.am.UserRecordBO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:03 2020/8/6
 * @version: 1.0.0
 * @description:
 */
@Data
public class UserRecordVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private String uname;

    private String oname;

    private Date ctime;

    private String type;

    public static List<UserRecordVO> convert(List<UserRecordBO> bos) {
        List<UserRecordVO> vos = new ArrayList<>(bos.size());
        for (UserRecordBO bo : bos) {
            vos.add(convert(bo));
        }
        return vos;
    }

    public static UserRecordVO convert(UserRecordBO bo) {
        UserRecordVO vo = new UserRecordVO();
        vo.setUname(bo.getUserName());
        vo.setOname(bo.getOperateName());
        vo.setCtime(bo.getCreateTime());
        vo.setType(bo.getType());
        return vo;
    }
}
