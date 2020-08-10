package com.smartindustry.common.pojo.om;

import java.io.Serializable;
import lombok.Data;

/**
 * om_logistics_picture
 * @author 
 */
@Data
public class LogisticsPicturePO implements Serializable {
    private Long logisticsPictureId;

    private Long logisticsRecordId;

    private String picture;

    private static final long serialVersionUID = 1L;
}