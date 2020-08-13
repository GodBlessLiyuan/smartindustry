package com.smartindustry.inventory.vo;

import com.smartindustry.common.bo.si.StorageLabelBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/8/13 9:42
 * @description: TODO
 * @version: 1.0
 */
@Data
public class StorageLabelVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    public static List<StorageLabelVO> convert(List<StorageLabelBO> bos) {
        return null;
    }
}
