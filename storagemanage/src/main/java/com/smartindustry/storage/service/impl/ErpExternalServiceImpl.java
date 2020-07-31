package com.smartindustry.storage.service.impl;

import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.service.IErpExternalService;
import com.smartindustry.storage.vo.ErpOrderVO;
import com.smartindustry.storage.vo.ReceiptPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/6/30 9:13
 * @description: ERP 外部接口
 * @version: 1.0
 */
@Service
public class ErpExternalServiceImpl implements IErpExternalService {
    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public ResultVO order(Map<String, Object> reqData) {

//        if (otype == 1) {
//            // po 收料单
//
//        } else if (otype == 3) {
//            // 生产退料
//        }

        // 测试数据
        ErpOrderVO vo = new ErpOrderVO();
        ErpOrderVO.ErpOrderHeadVO head = vo.getHead();
        head.setOno("PO2020020220200202");
        head.setOdate(new Date());
        head.setSupplier("东南院供应商");
        head.setBuyer("轩辕先生");
        head.setPdate(DateUtil.date2Str(new Date(), DateUtil.Y_M_D));
        List<ErpOrderVO.ErpOrderBodyVO> body = vo.getBody();
        ErpOrderVO.ErpOrderBodyVO bodyVO = new ErpOrderVO.ErpOrderBodyVO();
        bodyVO.setMid(1L);
        bodyVO.setMno("5101000496");
        bodyVO.setMtype((byte) 1);
        bodyVO.setMname("原料物料001");
        bodyVO.setMmodel("SH0001");
        bodyVO.setMdesc("测试物料，原料类型");
        bodyVO.setOtotal(1000);
        body.add(bodyVO);
        ErpOrderVO.ErpOrderBodyVO bodyVO2 = new ErpOrderVO.ErpOrderBodyVO();
        bodyVO2.setMid(2L);
        bodyVO2.setMno("5101000497");
        bodyVO2.setMtype((byte) 2);
        bodyVO2.setMname("半成品物料002");
        bodyVO2.setMmodel("SH0002");
        bodyVO2.setMdesc("测试物料2，半成品类型");
        bodyVO2.setOtotal(2000);
        body.add(bodyVO2);

        new Thread(() -> {
            // 更新物料信息
        }).start();

        return ResultVO.ok().setData(vo);
    }

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        List<ErpOrderVO.ErpOrderHeadVO> vos = new ArrayList<>();

        ErpOrderVO.ErpOrderHeadVO head1 = new ErpOrderVO.ErpOrderHeadVO();
        head1.setOno("PO2020070100001");
        head1.setOdate(new Date());
        head1.setSupplier("东南院供应商1");
        head1.setBuyer("轩辕先生1");
        head1.setPdate(DateUtil.date2Str(new Date(), DateUtil.Y_M_D));
        vos.add(head1);

        ErpOrderVO.ErpOrderHeadVO head2 = new ErpOrderVO.ErpOrderHeadVO();
        head2.setOno("PO2020070100002");
        head2.setOdate(new Date());
        head2.setSupplier("东南院供应商2");
        head2.setBuyer("轩辕先生2");
        head2.setPdate(DateUtil.date2Str(new Date(), DateUtil.Y_M_D));
        vos.add(head2);

        ErpOrderVO.ErpOrderHeadVO head3 = new ErpOrderVO.ErpOrderHeadVO();
        head3.setOno("PO2020070100003");
        head3.setOdate(new Date());
        head3.setSupplier("东南院供应商3");
        head3.setBuyer("轩辕先生3");
        head3.setPdate(DateUtil.date2Str(new Date(), DateUtil.Y_M_D));
        vos.add(head3);

        ErpOrderVO.ErpOrderHeadVO head4 = new ErpOrderVO.ErpOrderHeadVO();
        head4.setOno("PO2020070100004");
        head4.setOdate(new Date());
        head4.setSupplier("东南院供应商4");
        head4.setBuyer("轩辕先生4");
        head4.setPdate(DateUtil.date2Str(new Date(), DateUtil.Y_M_D));
        vos.add(head4);

        return ResultVO.ok().setData(new PageInfoVO<>(vos.size(), vos));
    }
}
