package com.smartindustry.datasynchronize.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.common.bo.ds.SaleOutboundErpBO;
import com.smartindustry.common.bo.ds.SalesOutboundBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.mapper.am.UserMapper;
import com.smartindustry.common.mapper.ds.SalesOutboundDetailMapper;
import com.smartindustry.common.mapper.ds.SalesOutboundMapper;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.ds.SalesOutboundDetailPO;
import com.smartindustry.common.pojo.ds.SalesOutboundPO;
import com.smartindustry.common.pojo.ds.sqlserver.SaleDetailErpPO;
import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.sqlserver.SaleOutboundErpMapper;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.datasynchronize.service.ISalesOutboundService;
import com.smartindustry.datasynchronize.vo.MaterialVO;
import com.smartindustry.datasynchronize.vo.SalesOutboundVO;
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
    private SalesOutboundMapper salesOutboundMapper;

    @Autowired
    private SalesOutboundDetailMapper salesOutboundDetailMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MaterialMapper materialMapper;

    /**
     * 销售出库 同步
     * @param reqData
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO sync(Map<String, Object> reqData) {
        List<SaleOutboundErpBO> bos = saleOutboundErpMapper.queryAll();
        Map<String, List<SalesOutboundDetailPO>> dpos = new HashMap<>(bos.size());
        List<SalesOutboundPO> pos = new ArrayList<>(bos.size());
        for (SaleOutboundErpBO bo: bos) {
            SalesOutboundPO po = new SalesOutboundPO();
            if (salesOutboundMapper.queryBySalesNo(po.getSalesNo()) != null) {
                continue;
            }
            po.setSalesNo(bo.getSaleNo());
            if(bo.getClientId() != null) {
                ClientPO client = clientMapper.queryByClientNo(bo.getClientNo());
                if (client == null) {
                    continue;
                }
                po.setClientId(client.getClientId());
            }
            po.setSalesDate(bo.getSaleDate());
            if (bo.getOperatorId() != null) {
                UserPO user = userMapper.queryByCode(bo.getOperatorCode());
                if (user == null) {
                    continue;
                }
                po.setUserId(user.getUserId());
            }
            po.setCreateTime(Calendar.getInstance().getTime());
            pos.add(po);
            if (bo.getSdpos() != null && !bo.getSdpos().isEmpty()) {
                dpos.put(po.getSalesNo(), convert(bo.getSdpos(), po.getSalesOutboundId()));
            }

        }
        if (!pos.isEmpty()) {
            salesOutboundMapper.batchInsert(pos);
        }
        List<SalesOutboundDetailPO> sodpos = new ArrayList<>();
        for (SalesOutboundPO po: pos) {
            List<SalesOutboundDetailPO> sps = dpos.get(po.getSalesNo());
            if (sps != null && !sps.isEmpty()) {
                for (SalesOutboundDetailPO p : sps) {
                    p.setSalesOutboundId(po.getSalesOutboundId());
                    sodpos.add(p);
                }
            }
        }
        if (!sodpos.isEmpty()) {
            salesOutboundDetailMapper.batchInsert(sodpos);
        }


        //查询
        Calendar c =Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY)-2);
        reqData.put("ctime", c.getTime());
        Page<Long> page = PageQueryUtil.startPage(reqData);
        List<SalesOutboundBO> list = salesOutboundMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), SalesOutboundVO.convert(list)));
    }

    private List<SalesOutboundDetailPO> convert(List<SaleDetailErpPO> sdpos, Long salesOutboundId) {
        List<SalesOutboundDetailPO> pos = new ArrayList<>(sdpos.size());
        for (SaleDetailErpPO ep: sdpos) {
            SalesOutboundDetailPO po = new SalesOutboundDetailPO();
            po.setSalesOutboundId(salesOutboundId);
            MaterialPO materialPO = materialMapper.queryByMaterialNo(ep.getMaterialNo(), ep.getMaterialName());
            if (materialPO == null) {
                continue;
            }
            po.setMaterialId(materialPO.getMaterialId());
            po.setNeedNum(BigDecimal.valueOf(ep.getSaleNum()));
            po.setUnitPrice(BigDecimal.valueOf(ep.getUnitPrice()));
            po.setTotalPrice(BigDecimal.valueOf(ep.getSum()));
            pos.add(po);
        }
        return pos;
    }
}
