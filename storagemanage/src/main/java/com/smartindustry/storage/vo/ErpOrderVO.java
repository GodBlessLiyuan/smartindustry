package com.smartindustry.storage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 9:17
 * @description: ERP 订单信息
 * @version: 1.0
 */
@Data
public class ErpOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private ErpOrderHeadVO head = new ErpOrderHeadVO();
    private List<ErpOrderBodyVO> body = new ArrayList<>();

    @Data
    public static class ErpOrderHeadVO {
        private String ono;
        private Date odate;
        private String supplier;
        private String buyer;
        private Date pdate;
    }

    @Data
    public static class ErpOrderBodyVO {
        private String mno;
        private Byte mtype;
        private String mname;
        private String mmodel;
        private String mdesc;
        private Integer ototal;
    }
}
