package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.LocationRecordBO;
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
 * @data 2020-11-11 20:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRecordVO implements Serializable {
    private String name;
    private String ctime;
    private String type;

    public static List<LocationRecordVO> convert(List<LocationRecordBO> bos) {
        if(bos==null||bos.size()==0){
            return new ArrayList<>(1);
        }
        List<LocationRecordVO> vos=new ArrayList<>(bos.size());
        for (LocationRecordBO bo:bos) {
            vos.add(new LocationRecordVO(bo.getName(), DateUtil.date2Str(bo.getCreateTime(),DateUtil.Y_M_D_T),bo.getOperationName()));
        }
        return vos;
    }
}
