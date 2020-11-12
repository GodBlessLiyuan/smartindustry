package com.smartindustry.basic.vo;

import com.smartindustry.common.bo.si.WarehouseRecordBO;
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
 * @data 2020-11-11 19:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRecordVO implements Serializable {
    private String name;
    private String ctime;
    private String type;

    public static List<WarehouseRecordVO> convert(List<WarehouseRecordBO> bos) {
        if(bos==null||bos.size()==0){
            return new ArrayList<>(1);
        }
        List<WarehouseRecordVO> vos=new ArrayList<>(bos.size());
        for (WarehouseRecordBO bo:bos) {
            vos.add(new WarehouseRecordVO(bo.getName(),DateUtil.date2Str(bo.getCreateTime(),DateUtil.Y_M_D_T),bo.getOperationName()));
        }
        return vos;
    }
}
