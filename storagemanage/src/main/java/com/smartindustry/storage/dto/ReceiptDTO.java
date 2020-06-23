package com.smartindustry.storage.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 16:29
 * @description: 收料管理
 * @version: 1.0
 */
@Data
public class ReceiptDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ReceiptHeadDTO head;
    private List<ReceiptBodyDTO> body;
}
