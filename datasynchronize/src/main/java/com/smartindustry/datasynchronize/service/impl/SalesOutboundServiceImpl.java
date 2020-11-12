package com.smartindustry.datasynchronize.service.impl;

import com.smartindustry.common.bo.ds.SaleOutboundErpBO;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.mapper.om.OutboundBodyMapper;
import com.smartindustry.common.mapper.om.OutboundHeadMapper;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.sqlserver.SaleOutboundErpMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.ds.sqlserver.SaleDetailErpPO;
import com.smartindustry.common.pojo.om.OutboundBodyPO;
import com.smartindustry.common.pojo.om.OutboundHeadPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.ISalesOutboundService;
import com.smartindustry.datasynchronize.util.OutboundNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author hui.feng
 * @date created in 2020/11/5
 * @description
 */
@Service
@EnableTransactionManagement
public class SalesOutboundServiceImpl implements ISalesOutboundService {


    @Autowired
    private SaleOutboundErpMapper saleOutboundErpMapper;

    @Autowired
    private OutboundHeadMapper outboundHeadMapper;

    @Autowired
    private OutboundBodyMapper outboundBodyMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 销售出库 同步
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO sync() {
        List<SaleOutboundErpBO> bos = saleOutboundErpMapper.queryAll();
        Map<String, List<OutboundBodyPO>> bpos = new HashMap<>(bos.size());
        List<OutboundHeadPO> pos = new ArrayList<>(bos.size());
        String outboundNo = OutboundNoUtil.genOutboundHeadNo(outboundHeadMapper,OutboundNoUtil.OUTBOUND_HEAD_XS,Calendar.getInstance().getTime());
        String prefix = outboundNo.substring(0,outboundNo.length()-5);
        Integer laterNum = Integer.valueOf(outboundNo.substring(outboundNo.length()-5));
        for (SaleOutboundErpBO bo: bos) {
            OutboundHeadPO po = new OutboundHeadPO();
            if (outboundHeadMapper.queryByOutboundNo(bo.getSaleNo()) != null) {
                continue;
            }
            po.setOutboundNo(prefix+laterNum++);
            po.setSourceNo(bo.getSaleNo());
            po.setClientNo(bo.getClientNo());
            clientMapper.queryByClientNo(bo.getClientNo());
            po.setSalesDate(bo.getSaleDate());
            if (bo.getOperatorId() != null) {
                UserPO user = userMapper.queryByCode(bo.getOperatorCode());
                if (user == null) {
                    continue;
                }
                po.setSalesId(user.getUserId());
            }
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setDr((byte)1);
            po.setStatus((byte)3);
            po.setSourceType((byte)2);

            if (bo.getSdpos() != null && !bo.getSdpos().isEmpty()) {
                List<OutboundBodyPO> bodyPOS = convert(bo.getSdpos());
                po.setExpectNum(BigDecimal.valueOf(bodyPOS.stream().mapToDouble(p->p.getExpectNum().doubleValue()).sum()));
                bpos.put(po.getOutboundNo(), bodyPOS);
            }
            pos.add(po);
        }
        if (!pos.isEmpty()) {
            outboundHeadMapper.batchInsert(pos);
        }
        List<OutboundBodyPO> bodyPos = new ArrayList<>();
        for (OutboundHeadPO po: pos) {
            List<OutboundBodyPO> bps = bpos.get(po.getOutboundNo());
            if (bps != null && !bps.isEmpty()) {
                for (OutboundBodyPO p : bps) {
                    p.setOutboundHeadId(po.getOutboundHeadId());
                    bodyPos.add(p);
                }
            }
        }
        if (!bodyPos.isEmpty()) {
            outboundBodyMapper.batchInsert(bodyPos);
        }
        return ResultVO.ok();
    }

    private List<OutboundBodyPO> convert(List<SaleDetailErpPO> sdpos) {
        List<OutboundBodyPO> pos = new ArrayList<>(sdpos.size());
        for (SaleDetailErpPO ep: sdpos) {
            OutboundBodyPO po = new OutboundBodyPO();
            MaterialPO materialPO = materialMapper.queryByMaterialNo(ep.getMaterialNo(), ep.getMaterialName());
            if (materialPO == null) {
                continue;
            }
            po.setMaterialId(materialPO.getMaterialId());
            po.setExpectNum(BigDecimal.valueOf(ep.getSaleNum()));
            po.setUnitPrice(BigDecimal.valueOf(ep.getUnitPrice()));
            po.setTotalPrice(BigDecimal.valueOf(ep.getSum()));
            po.setCreateTime(Calendar.getInstance().getTime());
            po.setExtra(ep.getRemark());
            po.setDr((byte)1);
            pos.add(po);
        }
        return pos;
    }
}
