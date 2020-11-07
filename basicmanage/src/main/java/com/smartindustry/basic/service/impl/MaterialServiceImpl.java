package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IMaterialService;

import com.smartindustry.basic.vo.MaterialVO;
import com.smartindustry.common.bo.si.MaterialAttributeBO;
import com.smartindustry.common.bo.si.MaterialBO;
import com.smartindustry.common.bo.si.MaterialRecordBO;
import com.smartindustry.common.config.FilePathConfig;
import com.smartindustry.common.constant.ResultConstant;
import com.smartindustry.common.mapper.im.MaterialInventoryMapper;
import com.smartindustry.common.mapper.im.SafeStockMapper;
import com.smartindustry.common.mapper.si.MaterialAttributeMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.si.MaterialRecordMapper;
import com.smartindustry.common.mapper.si.MaterialSpecificationMapper;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/7/29 9:19
 * @description: 物料管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class MaterialServiceImpl implements IMaterialService {
    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private MaterialRecordMapper materialRecordMapper;
    @Autowired
    private FilePathConfig filePathConfig;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<MaterialBO> page = PageQueryUtil.startPage(reqData);
        List<MaterialBO> bos = materialMapper.pageQueryStorage(reqData);

        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), MaterialVO.convert(bos, filePathConfig)));
    }

    @Override
    public ResultVO detail(OperateDTO dto) {
        MaterialBO materialBO = materialMapper.queryByMid(dto.getMid());
        if (null == materialBO) {
            return new ResultVO(1002);
        }

        MaterialVO vo = MaterialVO.convert(materialBO, filePathConfig);
        MaterialAttributeBO attributeBO = pu.detail(materialBO.getMaterialId());
        if (null != attributeBO) {
//            vo.setMattribute(MaterialAttributeVO.convert(attributeBO));
        }

        return ResultVO.ok().setData(vo);
    }


}
