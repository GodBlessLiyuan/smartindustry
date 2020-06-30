package com.smartindustry.storage.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IErpExternalService;
import com.smartindustry.storage.vo.ErpOrderVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 9:13
 * @description: ERP 外部接口
 * @version: 1.0
 */
@Service
public class ErpExternalServiceImpl implements IErpExternalService {
    @Override
    public ResultVO order(String ono, Byte otype) {
        if (otype == 1) {
            // po 收料单

        } else if (otype == 3) {
            // 生产退料
        }

        // 测试数据
        ErpOrderVO vo = new ErpOrderVO();
        ErpOrderVO.ErpOrderHeadVO head = vo.getHead();
        head.setOno("PO2020020220200202");
        head.setOdate(new Date());
        head.setSupplier("东南院供应商");
        head.setBuyer("轩辕先生");
        head.setPdate(new Date());
        List<ErpOrderVO.ErpOrderBodyVO> body = vo.getBody();
        ErpOrderVO.ErpOrderBodyVO bodyVO = new ErpOrderVO.ErpOrderBodyVO();
        bodyVO.setMno("5101000497");
        bodyVO.setMtype((byte) 1);
        bodyVO.setMname("合体");
        bodyVO.setMmodel("SH0001");
        bodyVO.setMdesc("测试物料，原料类型");
        bodyVO.setOtotal(5000);
        body.add(bodyVO);
        ErpOrderVO.ErpOrderBodyVO bodyVO2 = new ErpOrderVO.ErpOrderBodyVO();
        bodyVO2.setMno("5101000498");
        bodyVO2.setMtype((byte) 1);
        bodyVO2.setMname("合体2");
        bodyVO2.setMmodel("SH0002");
        bodyVO2.setMdesc("测试物料2，原料类型");
        bodyVO2.setOtotal(2000);
        body.add(bodyVO2);

        return ResultVO.ok().setData(vo);
    }
}
