package com.smartindustry.workbench.feign;

import com.smartindustry.common.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: xiahui
 * @date: Created in 2020/10/9 10:03
 * @description: TODO
 * @version: 1.0
 */
@FeignClient(name = "bigdatamanage")
public interface BigDataFeign {

    @PostMapping("wms")
    ResultVO wms();
}
