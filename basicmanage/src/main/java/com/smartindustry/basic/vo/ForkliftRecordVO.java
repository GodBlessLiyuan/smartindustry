package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.ForkliftRecordBO;
import com.smartindustry.common.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-11-10 14:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForkliftRecordVO implements Serializable {
    //操作人
    private String name;
    private String ctime;
    //操作类型
    private String type;


    public static List<ForkliftRecordVO> convert(List<ForkliftRecordBO> forkLiftRecordBOS) {
        if(forkLiftRecordBOS==null&&forkLiftRecordBOS.size()==0){
            return new ArrayList<>(1);
        }
        List<ForkliftRecordVO> vos=new ArrayList<>(forkLiftRecordBOS.size());
        for (ForkliftRecordBO bo:forkLiftRecordBOS) {
            vos.add(new ForkliftRecordVO(bo.getUserName(), DateUtil.date2Str(bo.getCreateTime(),DateUtil.Y_M_D_T),bo.getOperationName()));
        }
        return vos;
    }
}
