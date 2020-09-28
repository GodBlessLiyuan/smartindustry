package com.smartindustry.workbench.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.service.IBigDataService;
import com.smartindustry.workbench.vo.BigDataWmsVO;
import org.springframework.stereotype.Service;

/**
 * @author: xiahui
 * @date: Created in 2020/9/27 18:59
 * @description: 大数据服务
 * @version: 1.0
 */
@Service
public class BigDataServiceImpl implements IBigDataService {
    @Override
    public ResultVO wms() {
        BigDataWmsVO vo = new BigDataWmsVO();
        BigDataWmsVO.A osnum = new BigDataWmsVO.A();
        osnum.setTotal(1200);
        osnum.setMoy(12);
        osnum.setMom(11);
        osnum.setAverage(200);
        vo.setOsnum(osnum);

        BigDataWmsVO.B odrate = new BigDataWmsVO.B();
        odrate.setRate(90);
        odrate.setNum(50);
        vo.setOdrate(odrate);

        BigDataWmsVO.A osmoney = new BigDataWmsVO.A();
        osmoney.setTotal(500000);
        osmoney.setMoy(12);
        osmoney.setMom(11);
        osmoney.setAverage(10000);
        vo.setOsmoney(osmoney);

        BigDataWmsVO.A psmoney = new BigDataWmsVO.A();
        psmoney.setTotal(5000000);
        psmoney.setMoy(12);
        psmoney.setMom(11);
        psmoney.setAverage(200000);
        vo.setPsmoney(psmoney);

        BigDataWmsVO.A rimoney = new BigDataWmsVO.A();
        rimoney.setTotal(5000000);
        rimoney.setMoy(12);
        rimoney.setMom(11);
        vo.setRimoney(rimoney);

        BigDataWmsVO.A iimoney = new BigDataWmsVO.A();
        iimoney.setTotal(5000000);
        iimoney.setMoy(12);
        iimoney.setMom(11);
        vo.setIimoney(iimoney);

        BigDataWmsVO.A iturnover = new BigDataWmsVO.A();
        iturnover.setTotal(5);
        iturnover.setMoy(12);
        iturnover.setMom(11);
        vo.setIturnover(iturnover);

        return ResultVO.ok().setData(vo);
    }
}
