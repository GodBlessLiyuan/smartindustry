package com.smartindustry.workbench.service.impl;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.workbench.feign.BigDataFeign;
import com.smartindustry.workbench.service.IBigDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xiahui
 * @date: Created in 2020/9/27 18:59
 * @description: 大数据服务
 * @version: 1.0
 */
@Service
public class BigDataServiceImpl implements IBigDataService {
    @Autowired
    private BigDataFeign bigDataFeign;

    @Override
    public ResultVO wms() {
        return bigDataFeign.wms();
    }
}
