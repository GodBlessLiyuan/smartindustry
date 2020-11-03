package com.smartindustry.common.mapper.bim;

import com.smartindustry.common.mapper.BaseMapper;
import com.smartindustry.common.pojo.bim.ProductPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * ProductMapper继承基类
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductPO, Long> {
}