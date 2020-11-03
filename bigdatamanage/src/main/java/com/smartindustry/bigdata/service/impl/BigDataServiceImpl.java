package com.smartindustry.bigdata.service.impl;

import com.smartindustry.bigdata.service.IBigDataService;
import com.smartindustry.bigdata.vo.BigDataWmsVO;
import com.smartindustry.common.mapper.bd.*;
import com.smartindustry.common.pojo.bd.*;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xiahui
 * @date: Created in 2020/10/9 10:07
 * @description: 大数据查询
 * @version: 1.0
 */
@Service
public class BigDataServiceImpl implements IBigDataService {
    @Autowired
    private OrderGoodsNumMapper orderGoodsNumMapper;
    @Autowired
    private OrderGoodsRateMapper orderGoodsRateMapper;
    @Autowired
    private OrderGoodsFundsMapper orderGoodsFundsMapper;
    @Autowired
    private MaInputFundsMapper maInputFundsMapper;
    @Autowired
    private MaWareFundsMapper maWareFundsMapper;
    @Autowired
    private GoodsFundsMapper goodsFundsMapper;
    @Autowired
    private GoodsRotationRateMapper goodsRotationRateMapper;

    @Override
    public ResultVO wms() {
        BigDataWmsVO vo = new BigDataWmsVO();

        OrderGoodsNumPO orderGoodsNumPO = orderGoodsNumMapper.queryLastDay();
        if (null != orderGoodsNumPO) {
            BigDataWmsVO.A osnum = new BigDataWmsVO.A();
            osnum.setTotal(orderGoodsNumPO.getOrderGoodsNum());
            osnum.setMoy(orderGoodsNumPO.getSameRate());
            osnum.setMom(orderGoodsNumPO.getCircleRate());
            osnum.setAverage(orderGoodsNumPO.getGoodsNumDay());
            vo.setOsnum(osnum);
        }

        OrderGoodsRatePO orderGoodsRatePO = orderGoodsRateMapper.queryLastDay();
        if (null != orderGoodsRatePO) {
            BigDataWmsVO.B odrate = new BigDataWmsVO.B();
            odrate.setRate(orderGoodsRatePO.getOrderGoodsRate());
            odrate.setNum(orderGoodsRatePO.getGoodsNum());
            vo.setOdrate(odrate);
        }

        OrderGoodsFundsPO orderGoodsFundsPO = orderGoodsFundsMapper.queryLastDay();
        if (null != orderGoodsFundsPO) {
            BigDataWmsVO.A osmoney = new BigDataWmsVO.A();
            osmoney.setTotal(orderGoodsFundsPO.getGoodsFunds());
            osmoney.setMoy(orderGoodsFundsPO.getSameRate());
            osmoney.setMom(orderGoodsFundsPO.getCircleRate());
            osmoney.setAverage(orderGoodsFundsPO.getGoodsFundsDay());
            vo.setOsmoney(osmoney);
        }

        MaInputFundsPO maInputFundsPO = maInputFundsMapper.queryLastDay();
        if (null != maInputFundsPO) {
            BigDataWmsVO.A psmoney = new BigDataWmsVO.A();
            psmoney.setTotal(maInputFundsPO.getMaInputFunds());
            psmoney.setMoy(maInputFundsPO.getSameRate());
            psmoney.setMom(maInputFundsPO.getCircleRate());
            psmoney.setAverage(maInputFundsPO.getGoodsInputFunds());
            vo.setPsmoney(psmoney);
        }

        MaWareFundsPO maWareFundsPO = maWareFundsMapper.queryLastDay();
        if (null != maWareFundsPO) {
            BigDataWmsVO.A rimoney = new BigDataWmsVO.A();
            rimoney.setTotal(maWareFundsPO.getMaWareFunds());
            rimoney.setMoy(maWareFundsPO.getSameRate());
            rimoney.setMom(maWareFundsPO.getCircleRate());
            vo.setRimoney(rimoney);
        }

        GoodsFundsPO goodsFundsPO = goodsFundsMapper.queryLastDay();
        if (null != goodsFundsPO) {
            BigDataWmsVO.A iimoney = new BigDataWmsVO.A();
            iimoney.setTotal(goodsFundsPO.getGoodsFunds());
            iimoney.setMoy(goodsFundsPO.getSameRate());
            iimoney.setMom(goodsFundsPO.getCircleRate());
            vo.setIimoney(iimoney);
        }

        GoodsRotationRatePO goodsRotationRatePO = goodsRotationRateMapper.queryLastDay();
        if (null != goodsRotationRatePO) {
            BigDataWmsVO.A iturnover = new BigDataWmsVO.A();
            iturnover.setTotal(goodsRotationRatePO.getRotationNum());
            iturnover.setMoy(goodsRotationRatePO.getSameRate());
            iturnover.setMom(goodsRotationRatePO.getCircleRate());
            vo.setIturnover(iturnover);
        }

        return ResultVO.ok().setData(vo);
    }
}
