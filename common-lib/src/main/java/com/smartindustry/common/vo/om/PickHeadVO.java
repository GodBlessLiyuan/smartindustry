package com.smartindustry.common.vo.om;

import com.smartindustry.common.pojo.om.PickHeadPO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:06 2020/7/15
 * @version: 1.0.0
 * @description: 查询拣货单表头信息
 */
@Data
public class PickHeadVO implements Serializable {
    private static final long SerialVersionUID = 1L;
    /**
     * 拣货单号
     */
    private String pickNo;
    /**
     * 物料状态
     */
    private Byte materialStatus;
    /**
     * 工单号
     */
    private String orderNo;
    /**
     * 对应项目
     */
    private String correspondProject;
    /**
     * 计算发货时间
     */
    private Date planTime;
    /**
     * 出库时间
     */
    private Date outboundTime;
    /**
     * 出库情况
     */
    private Byte outboundStatus;

    public static List<PickHeadVO> convert(List<PickHeadPO> pos) {
        List<PickHeadVO> vos = new ArrayList<>();
        for (PickHeadPO po : pos) {
            PickHeadVO vo = new PickHeadVO();
            BeanUtils.copyProperties(po, vo);
            vos.add(vo);
        }
        return vos;
    }
}
