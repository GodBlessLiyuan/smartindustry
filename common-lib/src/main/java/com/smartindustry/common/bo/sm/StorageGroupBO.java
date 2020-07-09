package com.smartindustry.common.bo.sm;

import com.smartindustry.common.pojo.sm.StorageGroupPO;
import lombok.Data;

import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/7/8 19:00
 * @description: 物料入库组BO
 * @version: 1.0
 */
@Data
public class StorageGroupBO extends StorageGroupPO {

    List<StorageDetailBO> detail;
}
