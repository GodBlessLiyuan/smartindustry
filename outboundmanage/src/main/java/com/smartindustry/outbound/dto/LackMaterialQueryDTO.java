package com.smartindustry.outbound.dto;

import com.smartindustry.common.vo.om.ScanPickVO;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:02 2020/7/16
 * @version: 1.0.0
 * @description:  在欠料列表展示时包含物料编码和物料名称查询以及只看欠料的标志位
 */
@Data
public class LackMaterialQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pickNo;

    private String materialNo;

    private String materialName;

}
