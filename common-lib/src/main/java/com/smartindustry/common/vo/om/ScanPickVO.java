package com.smartindustry.common.vo.om;

import com.smartindustry.common.bo.om.ScanPickBO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 16:04 2020/7/15
 * @version: 1.0.0
 * @description: 扫码拣货检索列表
 */
@Data
public class ScanPickVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 打印标签的PID
     */
    private String packageId;

    /**
     * 物料名称
     */
    private String materialName;
    /**
     * 物料描述
     */
    private String materialDesc;
    /**
     * 数量
     */
    private Integer num;

    public static List<ScanPickVO> convert(List<ScanPickBO> bos) {
        List<ScanPickVO> vos = new ArrayList<>();
        for (ScanPickBO bo : bos) {
            ScanPickVO vo = new ScanPickVO();
            BeanUtils.copyProperties(bo, vo);
            vos.add(vo);
        }
        return vos;
    }
}
