package com.smartindustry.basic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/7/30 14:41
 * @description: 基础数据
 * @version: 1.0
 */
@Data
public class BasicDataDTO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long wtid;
    private Long ltid;
    private Long sgid;
    private Long csid;
    private Long stid;
    private Long spid;
    private Long cid;
    private Long mtid;
    private Long hlid;
    private Long mlid;
    private Long muid;
    private Long mvid;
    private Long pllid;
    private Long lcsid;
}
