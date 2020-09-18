package com.smartindustry.common.pojo.si;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * si_client_record
 * @author 
 */
@NoArgsConstructor
@Data
public class ClientRecordPO implements Serializable {
    private Long clientRecordId;

    private Long clientId;

    private Long userId;

    private Date createTime;

    private String type;

    public ClientRecordPO(Long clientId,Long userId,Date createTime,String type){
        this.clientId = clientId;
        this.userId = userId;
        this.createTime = createTime;
        this.type = type;
    }

    private static final long serialVersionUID = 1L;
}